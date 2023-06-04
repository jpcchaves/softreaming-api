package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.LineRating;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.entities.Rating;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;
import com.jpcchaves.softreaming.repositories.LineRatingRepository;
import com.jpcchaves.softreaming.repositories.MovieRepository;
import com.jpcchaves.softreaming.services.SecurityContextService;
import com.jpcchaves.softreaming.services.usecases.movie.UpdateMovieRatingUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

@Service
public class UpdateMovieRatingImpl implements UpdateMovieRatingUseCase {

    private final MovieRepository repository;
    private final SecurityContextService securityContextService;
    private final LineRatingRepository lineItemRepository;
    private final MovieUtils movieUtils;

    public UpdateMovieRatingImpl(MovieRepository repository,
                                 SecurityContextService securityContextService,
                                 LineRatingRepository lineItemRepository,
                                 MovieUtils movieUtils) {
        this.repository = repository;
        this.securityContextService = securityContextService;
        this.lineItemRepository = lineItemRepository;
        this.movieUtils = movieUtils;
    }

    @Override
    public ApiMessageResponseDto updateRating(Long id,
                                              Long ratingId,
                                              MovieRatingDto movieRatingDto) {
        Long userId = securityContextService.getCurrentLoggedUser().getId();

        LineRating lineRating = lineItemRepository
                .findByUserIdAndId(userId,
                        ratingId)
                .orElseThrow(() -> new BadRequestException("Ocorreu um erro ao editar sua avaliação. Verifique os dados e tente novamente"));

        lineRating.setRate(movieRatingDto.getRating());

        lineItemRepository.save(lineRating);

        Movie movie = movieUtils.getMovie(id);
        Rating movieRating = movie.getRatings();

        if (movieRating.getLineRatings().isEmpty()) {
            throw new BadRequestException("Ainda não há avaliações para o filme");
        }

        if (movieUtils.hasMoreThanOneRating(movieRating.getRatingsAmount())) {
            Double avgRating = movieUtils.calcRating(movie.getRatings().getLineRatings());

            movieRating.setRating(avgRating);
            movieRating.setRatingsAmount(movie.getRatings().getLineRatings().size());
        } else {
            movieRating.setRating(movieRatingDto.getRating());
        }

        repository.save(movie);

        return new ApiMessageResponseDto("Avaliação atualizada com sucesso!");
    }
}
