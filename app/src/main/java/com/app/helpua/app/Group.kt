package com.app.helpua.app


class Group(var name: String) {
    var children: MutableList<String> = ArrayList()
    fun addChild(name: String) {
        children.add(name)
    }

}