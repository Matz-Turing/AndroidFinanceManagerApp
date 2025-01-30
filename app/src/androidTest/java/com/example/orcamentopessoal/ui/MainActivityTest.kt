package com.example.orcamentopessoal.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.orcamentopessoal.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAdicionarReceita() {
        // Clicar no botão de adicionar receita
        onView(withId(R.id.btnReceita))
            .perform(click())

        // Verificar se o diálogo está visível
        onView(withId(R.id.edtDescricao))
            .check(matches(isDisplayed()))

        // Preencher os campos
        onView(withId(R.id.edtDescricao))
            .perform(typeText("Salário"), closeSoftKeyboard())
        
        onView(withId(R.id.edtValor))
            .perform(typeText("5000"), closeSoftKeyboard())

        // Confirmar a adição
        onView(withText("Adicionar"))
            .perform(click())

        // Verificar se a transação aparece na lista
        onView(withText("Salário"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAdicionarDespesa() {
        // Clicar no botão de adicionar despesa
        onView(withId(R.id.btnDespesa))
            .perform(click())

        // Preencher os campos
        onView(withId(R.id.edtDescricao))
            .perform(typeText("Aluguel"), closeSoftKeyboard())
        
        onView(withId(R.id.edtValor))
            .perform(typeText("1500"), closeSoftKeyboard())

        // Confirmar a adição
        onView(withText("Adicionar"))
            .perform(click())

        // Verificar se a transação aparece na lista
        onView(withText("Aluguel"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFabButton() {
        // Clicar no FAB
        onView(withId(R.id.fabAddTransaction))
            .perform(click())

        // Verificar se o diálogo está visível
        onView(withId(R.id.edtDescricao))
            .check(matches(isDisplayed()))
    }
}
