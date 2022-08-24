package com.example.perpus.module

data class user(val id:String,val kodebuku:String, val judulbuku:String) {
    constructor():this("", "", "")
    constructor(kodebuku: String, judulbuku: String) : this()
}