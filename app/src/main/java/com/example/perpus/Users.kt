package com.example.perpus.module

data class Users(val id:String,val nama:String,val nim:String,val email:String,val number:String,val password:String,val jurusan:String) {
    constructor():this("", "", "", "", "", "",  "")
}