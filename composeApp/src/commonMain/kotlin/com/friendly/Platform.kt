package com.friendly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform