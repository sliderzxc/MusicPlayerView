package com.sliderzxc.library.domain

import com.sliderzxc.library.view.ViewData

interface Init {

    fun initSeekBar(duration: Int)

    fun initTitle(title: String)

    fun initAuthor(author: String)

    fun initHandler()

    fun initView(viewData: ViewData)
}