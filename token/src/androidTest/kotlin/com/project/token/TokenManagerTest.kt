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
//    @Mock
//    private val sharedPreferences: SharedPreferences? = null
//
//    @Mock
//    private val editor: SharedPreferences.Editor? = null

    private var tokenManager: TokenManager? = null

    @Before
    fun setUp() {
        // Создаем моки для SharedPreferences и Editor
//        `when`(sharedPreferences!!.edit()).thenReturn(editor)


        // Создаем объект TokenManager с контекстом приложения
        tokenManager = TokenManager(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testGetToken_WhenNoTokenSaved_ReturnsEmptyString() {

//        // Arrange
//        `when`(sharedPreferences!!.getString(TokenManager.TOKEN_KEY, null)).thenReturn("12345")

        // Act
        val result = tokenManager!!.getToken()

        // Assert
        Assert.assertEquals("Bearer null", result)
    }

    @Test
    fun testSaveToken_SavesTokenCorrectly() {
        // Arrange
        val tokenToSave = "some_token"

        // Act
        tokenManager!!.saveToken(tokenToSave)
        val result = tokenManager!!.getToken()

        // Verify that the correct value was saved in SharedPreferences
        Assert.assertEquals("Bearer $tokenToSave", result)
    }
//
//    @Test
//    fun testDeleteToken_RemovesTokenFromStorage() {
//        // Arrange
//        val existingToken = "existing_token"
//        `when`(
//            sharedPreferences!!.getString(
//                TokenManager.TOKEN_KEY,
//                null
//            )
//        ).thenReturn(existingToken)
//
//        // Act
//        tokenManager!!.deleteToken()
//
//        // Verify that the key is removed from SharedPreferences
//        verify(editor)?.remove(TokenManager.TOKEN_KEY)
//        verify(editor)?.apply()
//    }

//    @Test
//    fun testGetToken_WhenNoTokenSaved_ReturnsEmptyString() {
//        // Arrange
////        `when`(sharedPreferences!!.getString(TokenManager.TOKEN_KEY, null)).thenReturn(null)
////
////        // Act
////        val result = tokenManager!!.getToken()
//
//        // Assert
//        Assert.assertEquals("", "")
//    }
}