package com.jpcchaves.softreaming.utils.movie;

import com.jpcchaves.softreaming.entities.Category;
import com.jpcchaves.softreaming.entities.LineRating;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.ResourceNotFoundException;
import com.jpcchaves.softreaming.payload.dtos.actor.ActorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.movie.*;
import com.jpcchaves.softreaming.payload.dtos.rating.RatingDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieUtils {
    public final MovieRepository repository;
    public final MapperUtils mapper;

    public MovieUtils(MovieRepository repository,
                      MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public static MovieResponseDto getMovieResponseDto(Movie movie,
                                                       MapperUtils mapper) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();

        for (Category category : movie.getCategories()) {
            movieResponseDto.getCategories().add(category.getCategory());
        }

        movieResponseDto.setId(movie.getId());
        movieResponseDto.setName(movie.getName());
        movieResponseDto.setShortDescription(movie.getShortDescription());
        movieResponseDto.setLongDescription(movie.getLongDescription());
        movieResponseDto.setDuration(movie.getDuration());
        movieResponseDto.setReleaseDate(movie.getReleaseDate());
        movieResponseDto.setMovieUrl(movie.getMovieUrl());
        movieResponseDto.setPosterUrl(movie.getPosterUrl());
        movieResponseDto.setCreatedAt(movie.getCreatedAt());
        movieResponseDto.setRatings(mapper.parseObject(movie.getRatings(),
                RatingDto.class));
        movieResponseDto.setDirectors(mapper.parseSetObjects(movie.getDirectors(), DirectorDto.class));
        movieResponseDto.setActors(mapper.parseSetObjects(movie.getActors(), ActorDto.class));

        return movieResponseDto;
    }

    public <T> MovieResponsePaginatedDto<?> buildMovieResponsePaginatedDto(List<T> moviesResponse,
                                                                           Page<Movie> moviePage) {
        MovieResponsePaginatedDto<T> moviePaginated = new MovieResponsePaginatedDto<>();

        moviePaginated.setContent(moviesResponse);
        moviePaginated.setPageNo(moviePage.getNumber());
        moviePaginated.setPageSize(moviePage.getSize());
        moviePaginated.setTotalElements(moviePage.getTotalElements());
        moviePaginated.setTotalPages(moviePage.getTotalPages());
        moviePaginated.setLast(moviePage.isLast());

        return moviePaginated;
    }

    public MovieResponseDto buildMovieResponseDto(Movie movie) {
        return getMovieResponseDto(movie, mapper);
    }

    public List<MovieByBestRatedDto> buildMovieListResponse(List<Movie> movieList) {
        List<MovieByBestRatedDto> bestRatedDtos = new ArrayList<>();

        for (Movie movie : movieList) {
            bestRatedDtos.add(new MovieByBestRatedDto(mapper.parseObject(movie, MovieResponseMinDto.class),
                    movie.getRatings().getRating(),
                    movie.getRatings().getRatingsAmount())
            );
        }

        return bestRatedDtos;
    }

    public Movie updateMovie(Movie movie,
                             MovieRequestDto requestDto) {
        movie.setId(movie.getId());
        movie.setCreatedAt(movie.getCreatedAt());
        movie.setName(requestDto.getName());
        movie.setShortDescription(requestDto.getShortDescription());
        movie.setLongDescription(requestDto.getLongDescription());
        movie.setDuration(requestDto.getDuration());
        movie.setMovieUrl(requestDto.getMovieUrl());
        movie.setPosterUrl(requestDto.getPosterUrl());

        return movie;
    }

    public Movie getMovie(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um filme com o ID  informado"));
    }

    public Boolean hasMoreThanOneRating(Integer ratingsAmount) {
        return ratingsAmount > 0;
    }

    public Double formatRating(Double rating) {
        BigDecimal bd = new BigDecimal(rating).setScale(2,
                RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    public Double calcRating(List<LineRating> lineRatingList) {
        Double avgRating = 0.0;

        for (LineRating lineRate : lineRatingList) {
            avgRating += lineRate.getRate();
        }

        return formatRating(avgRating / lineRatingList.size());
    }

}
