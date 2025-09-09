package com.miniproject2.mysalon.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
   MALE,FEMALE,KID,UNISEX,ALL;

   @JsonCreator
   public static Gender from(String s) {
      for (Gender g : Gender.values()) {
         if (g.name().equalsIgnoreCase(s)) {
            return g;
         }
      }
      // If no match, you might want to throw an exception or return a default value
      throw new IllegalArgumentException("Unknown gender: " + s);
   }
}
