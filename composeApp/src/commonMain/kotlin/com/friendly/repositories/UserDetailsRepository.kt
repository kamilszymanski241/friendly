package com.friendly.repositories

import com.friendly.dtos.UserDetailsDTO
import com.friendly.models.Gender
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserDetailsRepository: IUserDetailsRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getUserDetails(id: String): UserDetailsDTO {
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("UserDetails")
                    .select() {
                        filter {
                            eq("id", id)
                        }
                    }.decodeSingle<UserDetailsDTO>()
                result
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createUserDetails(userDetailsDTO: UserDetailsDTO): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("UserDetails").insert(userDetailsDTO)
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun changeName(userId: String, name: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("UserDetails").update(
                    {
                        set("name", name)
                    }
                ){
                    filter {
                        eq("id", userId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeSurname(userId: String, surname: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("UserDetails").update(
                    {
                        set("surname", surname)
                    }
                ){
                    filter {
                        eq("id", userId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeDateOfBirth(userId: String, dateOfBirth: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("UserDetails").update(
                    {
                        set("date_of_birth", dateOfBirth)
                    }
                ){
                    filter {
                        eq("id", userId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeGender(userId: String, gender: Gender): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("UserDetails").update(
                    {
                        set("gender", gender)
                    }
                ){
                    filter {
                        eq("id", userId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeDescription(userId: String, description: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("UserDetails").update(
                    {
                        set("description", description)
                    }
                ){
                    filter {
                        eq("id", userId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }
}