package com.example.project.api.main.response

class ComplaintResult {
    var status: String? = null
    var message: String? = null
}

class MediaResponse {
    var id: String? = null
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
    var status : Int = 1
    var title: String? = null
    var organizationalUnit: OrganizationalUnits? = null
    var expanded: Boolean = false

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