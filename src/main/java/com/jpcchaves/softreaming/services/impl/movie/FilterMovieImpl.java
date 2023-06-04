package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieByBestRatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponsePaginatedDto;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.usecases.movie.FilterMovie;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import com.jpcchaves.softreaming.utils.movie.MovieUtilsMethods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterMovieImpl implements FilterMovie {
    private final MovieRepository repository;
    private final MapperUtils mapper;
    private final MovieUtilsMethods movieUtils;

    public FilterMovieImpl(MovieRepository repository,
                           MapperUtils mapper,
                           MovieUtilsMethods movieUtils) {
        this.repository = repository;
        this.mapper = mapper;
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
