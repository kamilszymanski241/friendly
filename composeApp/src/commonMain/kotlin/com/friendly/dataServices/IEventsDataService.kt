package com.friendly.dataServices

import com.friendly.models.Event

interface IEventsDataService {
    public suspend fun getEventsWithUsers(): List<Event>
    public suspend fun createNewEvent(event: Event)
}