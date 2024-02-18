package org.project.movie;

import org.project.image.ImageDTO;

import java.util.List;

public record MovieDTO (Long imdbId, String title, Integer year, String description, List<ImageDTO> images) {

}
