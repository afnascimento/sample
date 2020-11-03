package com.unilever.julia.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.*
import android.net.Uri
import android.text.Spanned
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import org.apache.commons.lang3.StringUtils
import java.util.*

class Utils {

    class Styleable {
        companion object {
            fun getTypedArray(context: Context, attrs: AttributeSet?, @StyleableRes resStyleable : IntArray): TypedArray? {
                if (attrs != null) {
                    return context.theme.obtainStyledAttributes(attrs, resStyleable, 0, 0)
                }
                return null
            }

            @ColorInt
            fun getColor(context: Context,
                         typedArray : TypedArray?,
                         @StyleableRes resStyleable : Int,
                         @ColorRes defaultColor : Int): Int {
                if (typedArray != null) {
                    val color = typedArray.getColor(resStyleable, -1)
                    if (color != -1) {
                        return color
                    }
                }
                return ContextCompat.getColor(context, defaultColor)
            }

            fun getString(typedArray : TypedArray?,
                          @StyleableRes resStyleable : Int,
                          defaultValue : String) : String {
                return StringUtils.defaultIfBlank(typedArray?.getString(resStyleable) ?: "", defaultValue)
            }

            fun getBoolean(typedArray : TypedArray?,
                          @StyleableRes resStyleable : Int,
                          defaultValue : Boolean) : Boolean {
                return typedArray?.getBoolean(resStyleable, defaultValue) ?: defaultValue
            }

            fun getDrawable(context: Context,
                            typedArray : TypedArray?,
                            @StyleableRes resStyleable : Int,
                            @DrawableRes defaultDrawable : Int): Drawable? {
                if (typedArray != null) {
                    val resourceId = typedArray.getResourceId(resStyleable, -1)
                    if (resourceId != -1) {
                        return ContextCompat.getDrawable(context, resourceId)
                    }
                }
                return ContextCompat.getDrawable(context, defaultDrawable)
            }
        }
    }

    companion object {

        fun copyPasteContext(context: Context, text: String, textToast: String) {
            val cManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val cData = ClipData.newPlainText("text", text)
            cManager.primaryClip = cData

            showToast(context, textToast)
        }

        fun removeAccents(text : String): String {
            return JavaUtils.removeAccents(text)
        }

        fun removeCharactersInStringJson(json: String): String {
            var string = StringUtils.trimToEmpty(json)
            if (StringUtils.isNotEmpty(string)) {
                string = StringUtils.remove(string, "\\n")
                string = StringUtils.remove(string, "\\t")
                string = StringUtils.remove(string, "\\")
            }
            return string
        }

        fun parseStringToBoolean(value: String): Boolean {
            val string = StringUtils.lowerCase(StringUtils.deleteWhitespace(value))
            return StringUtils.equals(string, "true")
        }

        fun setFlagSecure(window: Window) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }

        fun textIcon(valHexStr : String): String {
            return JavaUtils.parseStringHexa(valHexStr)
        }

        fun parseJsonToMap(json: String): MutableMap<String, String> {
            return JavaUtils.parseJsonToMap(json)
        }

        fun parseJsonToListOfMap(json: String): MutableList<MutableMap<String, String>> {
            return try {
                JavaUtils.parseJsonToListOfMap(json)
            } catch (e : Throwable) {
                arrayListOf()
            }
        }

        fun showToast(context : Context, text : String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        fun openLinkInBrowser(activity: Activity, url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            activity.startActivity(intent)
        }

        fun getColorByHexa(colorHexa : String) : Int {
            return try {
                val textColorHexa = StringUtils.deleteWhitespace(colorHexa)
                when {
                    StringUtils.isEmpty(textColorHexa) -> 0
                    StringUtils.startsWith(textColorHexa, "#") -> Color.parseColor(textColorHexa)
                    else -> Color.parseColor("#$textColorHexa")
                }
            } catch (e : Throwable) {
                0
            }
        }

        fun setColorStroke(width : Int, color: Int, background: Drawable) {
            try {
                val contentCardBg : GradientDrawable = background as GradientDrawable
                contentCardBg.setStroke(width, color)
            } catch (e : Throwable) {
                e.printStackTrace()
            }
        }

        fun setColorDrawable(colorHexa: String, background: Drawable) {
            val color = getColorByHexa(colorHexa)
            if (color != 0) {
                setColorDrawable(color, background)
            }
        }

        fun getBackgroundRippleDrawable(
            context: Context,
            @ColorRes pressedColor: Int,
            backgroundDrawable: Drawable?
        ): RippleDrawable? {
            if (backgroundDrawable != null) {
                val pressedState = ContextCompat.getColorStateList(context, pressedColor)
                if (pressedState != null) {
                    return RippleDrawable(pressedState, backgroundDrawable, null)
                }
            }
            return null
        }

        fun setColorDrawable(@ColorInt color: Int, background: Drawable?): Drawable? {
            try {
                if (background != null) {
                    when (background) {
                        is ShapeDrawable -> background.paint.color = color
                        is GradientDrawable -> background.setColor(color)
                        is ColorDrawable -> background.color = color
                        is ClipDrawable -> background.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                    }
                }
                return background
            } catch (e : Throwable) {
                return background
            }
        }

        fun showKeyboard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            } catch (e : Throwable) {
                e.printStackTrace()
            }
        }

        fun hideKeyboard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e : Throwable) {
                e.printStackTrace()
            }
        }

        fun roundToDecimals(number: Double, numDecimalPlaces: Int): Double {
            val factor = Math.pow(10.0, numDecimalPlaces.toDouble())
            return Math.round(number * factor) / factor
        }

        fun toStringByDate(date: Date, format: String): String {
            return JavaUtils.getStringByDateAndFormat(date, format)
        }

        fun toDate(date: String, format: String): Date? {
            return JavaUtils.toDate(date, format)
        }

        fun removeTime(date: Date): Date {
            return JavaUtils.removeTime(date)
        }

        fun getDayInMonth(date: Date?): Int {
            return JavaUtils.getDayInMonth(date)
        }

        fun getMonthAbreviate(date: Date?): String {
            return JavaUtils.getMonthAbreviate(date)
        }

        fun getYearInDate(date: Date?): Int {
            return JavaUtils.getYearInDate(date)
        }

        fun dateInPeriod(date: Date?, period : Pair<Date, Date>?): Boolean {
            if (date != null && period != null) {
                return JavaUtils.dateInPeriod(date, period.first, period.second)
            }
            return false
        }

        fun getTextFromHtml(text: String): Spanned {
            return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
            /*
            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
                //Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(text)
            }
            */
        }

        fun getDimension(resources: Resources, value: Float, typedValue: Int): Float {
            if (value > 0f) {
                return TypedValue.applyDimension(typedValue, value, resources.displayMetrics)
            }
            return 0f
        }

        fun getStringByStyleable(typedArray : TypedArray, indexStyleable : Int, valueDefault : String): String {
            val tempValue = StringUtils.trimToEmpty(typedArray.getString(indexStyleable))
            if (StringUtils.isEmpty(tempValue)) {
                return valueDefault
            }
            return tempValue
        }

        fun isInteger(value: Double): Boolean {
            val split = StringUtils.split(value.toString(), ".")

            val decimal = split[1].toInt()

            return decimal == 0
        }

        fun setProgressColor(@ColorInt colorBackground: Int,
                             @ColorInt colorProgress: Int,
                             progressBar: ProgressBar) {
            val progressBarDrawable : LayerDrawable = progressBar.progressDrawable as LayerDrawable
            val backgroundDrawable = progressBarDrawable.getDrawable(0)
            val progressDrawable = progressBarDrawable.getDrawable(1)

            backgroundDrawable.setColorFilter(colorBackground, PorterDuff.Mode.SRC_IN)
            progressDrawable.setColorFilter(colorProgress, PorterDuff.Mode.SRC_IN)
        }

        fun getTextPercent(value: Double): String {
            val roundValue = roundDecimal(value, 2)
            return if (isInteger(roundValue)) {
                "${roundValue.toInt()}%"
            } else {
                "$roundValue%"
            }
        }

        fun getScreenResolution(context: Context): Pair<Int, Int> {
            val manager : WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            return Pair(metrics.widthPixels, metrics.heightPixels)
        }

        fun getDensity(context: Context): String {
            val densityDpi = context.resources.displayMetrics.densityDpi

            when (densityDpi) {
                DisplayMetrics.DENSITY_LOW -> return "LDPI"

                DisplayMetrics.DENSITY_MEDIUM -> return "MDPI"

                DisplayMetrics.DENSITY_HIGH -> return "HDPI"

                DisplayMetrics.DENSITY_XHIGH -> return "XHDPI"

                DisplayMetrics.DENSITY_XXHIGH -> return "XXHDPI"

                DisplayMetrics.DENSITY_XXXHIGH -> return "XXXHDPI"

                else -> return "NOT_FOUND"
            }
        }

        fun roundDecimal(value: Double, scale: Int) : Double {
            return NumberUtils.roundDecimal(value, scale)
        }
    }
}