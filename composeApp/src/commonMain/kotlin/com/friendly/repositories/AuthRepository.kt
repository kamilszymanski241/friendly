package com.friendly.repositories

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthRepository: IAuthRepository, KoinComponent {

    private val auth: Auth by inject()

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signOut(): Boolean {
        return try {
            auth.signOut()
            true
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun updateEmail(emailReceived: String): Boolean{
        return try {
            auth.updateUser(updateCurrentUser = true) {
                email = emailReceived
            }
            signOut()
            true
        }catch(e: Exception){
            println(e.message)
            throw e
        }
    }

    override suspend fun updatePassword(passwordReceived: String): Boolean{
        return try {
            auth.updateUser(updateCurrentUser = true) {
                password = passwordReceived
            }
            signOut()
            true
        }catch(e: Exception){
            println(e.message)
            throw e
        }
    }
}