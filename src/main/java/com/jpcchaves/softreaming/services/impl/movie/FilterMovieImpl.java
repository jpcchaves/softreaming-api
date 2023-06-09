package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.FilterMovieUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterMovieImpl implements FilterMovieUseCase {
    private final MovieRepository repository;
    private final MovieUtils movieUtils;

    public FilterMovieImpl(MovieRepository repository,
                           MovieUtils movieUtils) {
        this.repository = repository;
        this.movieUtils = movieUtils;
    }

    @Override
    public MovieResponsePaginatedDto<?> filterBy(Pageable pageable,
                                                 String releaseDate,
                                                 String name) {
        if (releaseDate == null && name == null) {
            throw new BadRequestException("Informe ao menos um filtro para conseguir realizar a busca");
        }

        if (releaseDate != null && name != null) {
            Page<Movie> moviePage = repository.findByNameContainingIgnoreCaseAndReleaseDate(pageable,
                    name,
                    releaseDate);
            List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(moviePage.getContent());

            return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, moviePage);
        }

        Page<Movie> moviePage;
        if (releaseDate != null) {
            moviePage = repository.findByReleaseDate(pageable,
                    releaseDate);
        } else {
            moviePage = repository.findByNameContainingIgnoreCase(pageable,
                    name);
        }
        List<MovieByBestRatedDto> movieByBestRatedDto = movieUtils.buildMovieListResponse(moviePage.getContent());

        return movieUtils.buildMovieResponsePaginatedDto(movieByBestRatedDto, moviePage);
    }
}
