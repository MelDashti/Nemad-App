package com.example.project.api.main.response

class ComplaintResult {
    var trackingNumber: String? = null
    var title: String? = null
}

class MediaResponse(var id: String? = null, var url: String? = null, var title: String?) {
}


class UserInfo {
    var id: String? = null
    var firstName: String? = null
    var nationalId: String? = null
    var lastName: String? = null

}


class Requests {
    var employeeName: String? = null
    var comments: String? = null
    lateinit var id: String
    var status: Long = 0L
    var statusStr: String? = null
    var rating : Long = 0L
    var sent: String? = null
    var title: String? = null
    var trackingNumber: String? = null
    var organizationalUnit: OrganizationalUnits? = null
    var category: Category? = null
    var expanded: Boolean = false
    var proceedings: List<Proceeding>? = null

}

class Proceeding() {
    var id: String? = null
    var dateTime: String? = "تاریخ: 26 مرداد 1401و 15:42"
    var comments: String? = "دیتا تستی"
    var attachments: List<Attachment>? = null

}

class Attachment() {
    var url: String? = null
    var title: String? = null
}


class Category() {
    var parentId: Long? = null
    var id: Long = 0
    var title: String? = null
    var description: String? = null
    var children: List<Category>? = null
}

class OrganizationalUnits {

    var parentId: Long? = null
    var id: Long = 0
    var title: String? = null
    var description: String? = null
    var children: List<OrganizationalUnits>? = null

}


class Organization {
    var id: Long = 0
    var title: String? = null
    var description: String? = null
}