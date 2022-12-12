package com.example.lab.service

import com.example.lab.model.User
import org.hibernate.search.jpa.FullTextQuery
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
internal class UserSearch(@PersistenceContext val entityManager: EntityManager) {

    init {
        searchUsers("danila")
    }

    fun searchUsers(text: String): List<User> {
        //извлекаем fullTextEntityManager, используя entityManager
        val fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager)

        // создаем запрос при помощи Hibernate Search query DSL
        val queryBuilder = fullTextEntityManager.searchFactory
            .buildQueryBuilder().forEntity(User::class.java).get()

        //обозначаем поля, по которым необходимо произвести поиск
        val query = queryBuilder
            .keyword()
            .onFields("name")
            .matching(text)
            .createQuery()

        //оборачиваем Lucene Query в Hibernate Query object
        val jpaQuery: FullTextQuery = fullTextEntityManager.createFullTextQuery(query, User::class.java)
        //возвращаем список сущностей
        val result = jpaQuery.resultList

        return result.map { it as User }.toList()
    }
}