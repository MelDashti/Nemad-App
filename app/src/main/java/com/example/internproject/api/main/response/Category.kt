package com.example.internproject.api.main.response

class ComplaintResult {
    var status: String? = null
    var message: String? = null
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