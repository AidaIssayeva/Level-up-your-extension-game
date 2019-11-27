package com.aida.levelupyourextensiongame

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var mNumActiveLoadingIndicators: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val domesticAnimal = DomesticAnimal(
            id = "123",
            name = "Bactrian Camel",
            logo = "some url",
            longDescription = "This is very long des",
            shortDescription = "this is very short des",
            type = Type.CAMEL,
            farmLocation = "Aral Sea"
        )
        val animal = domesticAnimal.toAnimal()
        tv.text = animal.name

        val wildAnimal = WildAnimal(
            id = "1223",
            name = "Bactrian Camel",
            logo = "some url",
            longDescription = "This is very long des",
            shortDescription = "this is very short des",
            inRedList = true,
            location = Biome.MOUNTAIN
        )
        val animal1 = wildAnimal.toAnimal()

        et.text.toAnimal() //<--text from editText can be mapped to animal, why would you want to do so?!

        tv.text = oldWayOfUsingSpansWithHtml()
        tv.movementMethod = LinkMovementMethod.getInstance()

        tv.text = spansWithExtensions()

    }

    fun showLoading() {
        mNumActiveLoadingIndicators++

        runOnUiThread {
            if (progressBar != null && progressBar!!.visibility != View.VISIBLE) {
                //lottieView!!.setAnimation("white_loader.json") <-some lottie animation
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method uses the extensions for SpannableStringBuilder (core-ktx library)
     * lineBreak() and space() are custom extensions
     */
    private fun spansWithExtensions(): SpannedString {
        return buildSpannedString {
            bold { color(Color.WHITE) { scale(2f) { append(getString(R.string.what_is_android)) } } }
            lineBreak()
            color(ContextCompat.getColor(this@MainActivity, R.color.colorAccent)) {
                append(
                    getString(R.string.what_is_android_answer)
                )
            }
            lineBreak()
            color(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.dirty_white
                )
            ) { append(getString(R.string.faq)) }
            space()
            underline {
                inSpans(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            //open webview
                            val url = getString(R.string.android_website)
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        }
                    },
                    ForegroundColorSpan(Color.GREEN)

                ) {
                    append("Android website.")
                }
            }
        }
    }

    /**
     * This method doesn't use any extensions
     */
    private fun spansWithoutExtensions(): SpannableStringBuilder {
        val firstPart = getString(R.string.what_is_android)
        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append(firstPart)
        spannableStringBuilder.append("\n")
        spannableStringBuilder.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            firstPart.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(2f), 0,
            firstPart.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            firstPart.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        val secondPart = getString(R.string.what_is_android_answer)
        spannableStringBuilder.append(secondPart)
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.colorAccent
                )
            ),
            firstPart.length,
            firstPart.length + secondPart.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.append("\n")
        val thirdPart = getString(R.string.faq)
        val beginning = firstPart.length + secondPart.length + 2
        val ending = beginning + thirdPart.length
        spannableStringBuilder.append(thirdPart)
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.dirty_white
                )
            ),
            beginning,
            ending,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.append(" ")
        val clickableText = "Android website."
        spannableStringBuilder.append(clickableText)
        spannableStringBuilder.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    //open webview
                    val url = getString(R.string.android_website)
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = true
                }
            }, spannableStringBuilder.length - clickableText.length,
            spannableStringBuilder.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(Color.GREEN),
            spannableStringBuilder.length - clickableText.length,
            spannableStringBuilder.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return spannableStringBuilder
    }

    /**
     * When we have complex string styling, we usually wrap it
    into html(see strings.xml) and then unwrap and apply clickable span
     */
    private fun oldWayOfUsingSpansWithHtml(): SpannableStringBuilder {
        val text = getString(R.string.text_html)
        val strBuilder = SpannableStringBuilder(Html.fromHtml(text))
        val urls = strBuilder.getSpans(0, text.length, URLSpan::class.java)
        for (span in urls) {
            val start = strBuilder.getSpanStart(span)
            val end = strBuilder.getSpanEnd(span)
            val flags = strBuilder.getSpanFlags(span)
            val clickable = object : ClickableSpan() {
                override fun onClick(view: View) {
                    val url = span.url
                    if ("support" == url) {
                        //display some third-party messenger
                    } else if (url.startsWith("tel:")) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    } else {
                        //open webview
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }

                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = true
                }
            }
            strBuilder.setSpan(clickable, start, end, flags)
            strBuilder.removeSpan(span)
        }
        return strBuilder
    }
}
