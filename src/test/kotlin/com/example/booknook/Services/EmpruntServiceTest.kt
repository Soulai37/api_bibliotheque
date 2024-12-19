package com.example.booknook

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.oauth2.jwt.Jwt
import java.time.LocalDate

class EmpruntServiceTest {

    private lateinit var empruntDAO: EmpruntDAOImpl
    private lateinit var empruntService: EmpruntService

    @BeforeEach
    fun setUp() {
        empruntDAO = mock()
        empruntService = EmpruntService(empruntDAO)
    }

    @Test
    fun `test obtenirEmprunts returns all emprunts`() {
        val emprunts = listOf(mockEmprunt(), mockEmprunt())
        `when`(empruntDAO.chercherTous()).thenReturn(emprunts)

        val result = empruntService.obtenirEmprunts()

        assertEquals(emprunts, result)
        verify(empruntDAO).chercherTous()
    }

    @Test
    fun `test obtenirEmpruntParId with valid user`() {
        val jwt = mockJwt("user@example.com")
        val emprunt = mockEmprunt(utilisateurLogin = "user@example.com")
        `when`(empruntDAO.chercherParId(1)).thenReturn(emprunt)

        val result = empruntService.obtenirEmpruntParId(1, jwt)

        assertEquals(emprunt, result)
        verify(empruntDAO).chercherParId(1)
    }

    @Test
    fun `test obtenirEmpruntParId throws exception for unauthorized user`() {
        val jwt = mockJwt("otheruser@example.com")
        val emprunt = mockEmprunt(utilisateurLogin = "user@example.com")
        `when`(empruntDAO.chercherParId(1)).thenReturn(emprunt)

        assertThrows(OperationNonAutoriseeException::class.java) {
            empruntService.obtenirEmpruntParId(1, jwt)
        }
    }

    @Test
    fun `test supprimerEmprunt with valid id`() {
        `when`(empruntDAO.effacer(1)).thenReturn(Unit)

        assertDoesNotThrow {
            empruntService.supprimerEmprunt(1)
        }
        verify(empruntDAO).effacer(1)
    }

    @Test
    fun `test supprimerEmprunt throws exception for invalid id`() {
        assertThrows(BadRequestException::class.java) {
            empruntService.supprimerEmprunt(-1)
        }
    }

    private fun mockJwt(email: String): Jwt {
        val jwt = mock(Jwt::class.java)
        `when`(jwt.claims).thenReturn(mapOf("email" to email))
        return jwt
    }

    private fun mockEmprunt(
        utilisateurLogin: String = "user@example.com"
    ): Emprunt {
        val utilisateur = Utilisateurs(1, "User", utilisateurLogin, false)
        val livre = Livres("123456", "Test Book", "Author", "Summary", "Edition", "Genre", 1, "image.jpg")
        return Emprunt(1, livre, utilisateur, LocalDate.now(), LocalDate.now().plusDays(7))
    }
}