package com.app.helpua.domain.models

import kotlinx.serialization.Serializable

@Serializable
class Post {

    var author: String? = null
        private set
    var title: String? = null
        private set
    var text: String? = null
        private set
    var image: String? = null
        private set

    var type: Int? = null
        private set

    private constructor() {}
    constructor(author: String?, text: String?, title: String?, image: String?, type: Int?) {
        this.author = author
        this.text = text
        this.title = title
        this.image = image
        this.type = type
    }
}