package br.com.andre.dto.lastfm;


import org.jboss.resteasy.reactive.RestQuery;

import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.DefaultValue;

public record LastFMRequestParams(
  @NotBlank(message = "user is required") @RestQuery("user") String user,
  @RestQuery("size") @DefaultValue("5") int size,
  @RestQuery("period") @DefaultValue("overall") String period
) {}
