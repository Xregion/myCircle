package com.adc.mycircle;

import android.graphics.Color;
import android.media.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventContent {

    public static final List<Event> ITEMS = new ArrayList<Event>();

    // TODO: Allow user to set up variables for events
    private static Event createEvent(int position, String groupName, String eventTitle, String details, Color backgroundColor, Image backgroundImage) {
        return new Event(String.valueOf(position), groupName, eventTitle, details, backgroundColor, backgroundImage);
    }

    /**
     * An Event item that will be created by groups
     */
    public static class Event {
        public final String id;
        public final String groupName;
        public final String eventTitle;
        public final String details;
        public final Color backgroundColor;
        public final Image backgroundImage;

        public Event(String id, String groupName, String eventTitle, String details, Color backgroundColor, Image backgroundImage) {
            this.id = id;
            this.groupName = groupName;
            this.eventTitle = eventTitle;
            this.details = details;
            this.backgroundColor = backgroundColor;
            this.backgroundImage = backgroundImage;
        }

        @Override
        public String toString() {
            return eventTitle;
        }
    }
}
