package com.example.project.util

import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import com.example.project.R


@BindingAdapter("details")
fun TextView.setDetails(details: Pair<String?, String?>?) {
    val phoneCodeColor = ContextCompat.getColor(this.context, R.color.req_text_color)
    val text = SpannableStringBuilder()

    if (details != null) {
        text.append(context.getString((R.string.string_detail))).append(" ").append(details.first).append()
            .append(context.getString((R.string.complaint_number))).append(" ").append(details.second)
    }
    this.text = text
}


@BindingAdapter("status")
fun TextView.setSrc(type: Long?) {

    var text = ""
    when (type) {
        0L -> {
//            bind.toggle.setImageResource(R.drawable.waitingforacceptance)
            text = "در انتزار تایید"
        }
        1L -> {
//            bind.toggle.setImageResource(R.drawable.underorganizatoininspection)
            text = "در حال بررسی"
        }

        2L -> {
//            bind.toggle.setImageResource(R.drawable.waitingforconfirmation)
            text = "بررسی شده"
        }
        3L -> {
//            bind.toggle.setImageResource(R.drawable.underorganizatoininspection)
            text = "برسسی مجدد "
        }
        4L -> {
//            bind.toggle.setImageResource(R.drawable.done)
            text = "پایان یافته"
        }
    }

    this.text = text

}


@BindingAdapter("type")
fun ImageView.setSrc(type: Long?) {
    when (type) {
        0L -> {
            this.setImageResource(R.drawable.waitingforacceptance)
        }
        1L -> {
            this.setImageResource(R.drawable.underorganizatoininspection)
        }

        2L -> {
            this.setImageResource(R.drawable.waitingforconfirmation)
        }
        3L -> {
            this.setImageResource(R.drawable.underorganizatoininspection)
        }
        4L -> {
            this.setImageResource(R.drawable.done)
        }
    }


}


@BindingAdapter("colored_text")
fun TextView.setTextColor(string: String?) {
    val phoneCodeColor = ContextCompat.getColor(this.context, R.color.hint_color)
    val text = SpannableStringBuilder().append(context.getString(R.string.signup_question))
    text.color(phoneCodeColor) {
        append(" $string")
    }
    this.text = text
}


@BindingAdapter(value = ["app:req_name", "app:type"], requireAll = false)
fun TextView.setTextVal(req_name: String?, type: String) {


    val phoneCodeColor = ContextCompat.getColor(this.context, R.color.req_text_color)
    val text = SpannableStringBuilder()

    when (type) {
        "req_type" -> text.append(context.getString((R.string.req_type)))
        "req_code" -> text.append(context.getString((R.string.req_code)))
        "req_description" -> text.append(context.getString((R.string.req_description)))
        "employee_name" -> text.append(context.getString((R.string.employee_name)))
        "org_unit_name" -> text.append(context.getString((R.string.org_unit_name)))
    }

    if (!req_name.isNullOrEmpty()) {
        text.color(phoneCodeColor) {
            append(" $req_name")
        }
    }
    this.text = text

}

