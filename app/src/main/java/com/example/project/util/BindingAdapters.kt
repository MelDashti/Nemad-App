package com.example.project.util

import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import com.example.project.R


@BindingAdapter("status")
fun TextView.setSrc(type: String?) {

    var text = ""
    when (type) {
        "WaitingForAcceptance" -> {
            text = "در انتزار تایید"
        }
        "WaitingForConfirmation" -> {
            text = "بررسی شده"
        }

        "UnderOrganizationalInspection" -> {
            text = "در حال بررسی"
        }
        "Done" -> {
            text = "انجام شده"
        }
    }

    this.text = text

}


@BindingAdapter("type")
fun ImageView.setSrc(type: String?) {

    when (type) {
        "WaitingForAcceptance" -> {
            this.setImageResource(R.drawable.waitingforacceptance)
        }
        "WaitingForConfirmation" -> {
            this.setImageResource(R.drawable.waitingforconfirmation)
        }

        "UnderOrganizationalInspection" -> {
            this.setImageResource(R.drawable.underorganizatoininspection)
        }
        "Done" -> {
            this.setImageResource(R.drawable.done)
        }
    }

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

