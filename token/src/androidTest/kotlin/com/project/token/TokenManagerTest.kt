package com.project.token

import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TokenManagerTest {
    private var tokenManager: TokenManager? = null

    @Before
    fun setUp() {
        tokenManager = TokenManager(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testGetToken_WhenNoTokenSaved_ReturnsEmptyString() {

        val result = tokenManager!!.getToken()

        Assert.assertEquals("Bearer null", result)
    }

    @Test
    fun testSaveToken_SavesTokenCorrectly() {
        val tokenToSave = "some_token"

        tokenManager!!.saveToken(tokenToSave)
        val result = tokenManager!!.getToken()

        Assert.assertEquals("Bearer $tokenToSave", result)
    }

    @Test
    fun testDeleteToken_RemovesTokenFromStorage() {
        val tokenToSave = "some_token"

        tokenManager!!.saveToken(tokenToSave)
        tokenManager!!.deleteToken()

        val result = tokenManager!!.getToken()

        Assert.assertEquals("Bearer null", result)
    }
}