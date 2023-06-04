package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.LineRating;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.entities.Rating;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRatingDto;
import com.jpcchaves.softreaming.repositories.LineRatingRepository;
import com.jpcchaves.softreaming.repositories.RatingRepository;
import com.jpcchaves.softreaming.services.SecurityContextService;
import com.jpcchaves.softreaming.services.usecases.movie.AddMovieRatingUseCase;
import com.jpcchaves.softreaming.utils.movie.MovieUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddMovieRatingImpl implements AddMovieRatingUseCase {
    private final SecurityContextService securityContextService;
    private final MovieUtils movieUtils;
    private final LineRatingRepository lineItemRepository;
    private final RatingRepository ratingRepository;

    public AddMovieRatingImpl(SecurityContextService securityContextService,
                              MovieUtils movieUtils,
                              LineRatingRepository lineItemRepository,
                              RatingRepository ratingRepository) {
        this.securityContextService = securityContextService;
        this.movieUtils = movieUtils;
        this.lineItemRepository = lineItemRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public ApiMessageResponseDto addMovieRating(Long id,
                                                MovieRatingDto movieRatingDto) {
        Long userId = securityContextService.getCurrentLoggedUser().getId();
        Movie movie = movieUtils.getMovie(id);
        Rating movieRating = movie.getRatings();

        if (lineItemRepository.existsByUserIdAndRating_Id(userId,
                movieRating.getId())) {
            throw new BadRequestException("Você já avaliou o filme: " + movie.getName());
        }

        LineRating newLineRating = new LineRating(movieRatingDto.getRating(),
                userId,
                movieRating);
        lineItemRepository.save(newLineRating);


        movieRating.setRatingsAmount(movieRating.getRatingsAmount() + 1);

        List<LineRating> lineRatingList = lineItemRepository.findAllByRating_Id(movieRating.getId());

        if (movieUtils.hasMoreThanOneRating(movieRating.getRatingsAmount())) {
            Double avgRating = movieUtils.calcRating(lineRatingList);

            movieRating.setRating(avgRating);
            movieRating.setRatingsAmount(lineRatingList.size());
        } else {
            movieRating.setRating(movieRatingDto.getRating());
        }

        ratingRepository.save(movieRating);

        return new ApiMessageResponseDto("Filme avaliado com sucesso");
    }
}
