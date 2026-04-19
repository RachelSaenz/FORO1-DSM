package com.example.foro1dsm.data

import com.example.foro1dsm.util.hashSHA256

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(name: String, email: String, password: String) {
        userDao.insertUser(
            User(name = name, email = email, password = hashSHA256(password))
        )
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun isEmailTaken(email: String): Boolean {
        return userDao.getUserByEmail(email) != null
    }
}
