package org.project.movie;

import org.project.image.ImageDTO;

import java.util.Set;

public record MovieDTO (Long imdbId, String title, Integer year, String description, Set<ImageDTO> images) {

}
