package org.project.movie;

import org.project.image.ImageDTO;

import java.util.Set;

public record MovieDTO (Long imdbId, String title, String description, Integer year, Set<ImageDTO> images) {

}
