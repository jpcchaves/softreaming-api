package com.jpcchaves.softreaming.services.impl.movie;

import com.jpcchaves.softreaming.entities.*;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.exceptions.SqlBadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieRequestDto;
import com.jpcchaves.softreaming.repositories.*;
import com.jpcchaves.softreaming.services.usecases.movie.CreateMovieUseCase;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CreateMovieImpl implements CreateMovieUseCase {

    private final MovieRepository repository;
    private final CategoryRepository categoryRepository;
    private final RatingRepository ratingRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MapperUtils mapper;

    public CreateMovieImpl(MovieRepository repository,
                           CategoryRepository categoryRepository,
                           RatingRepository ratingRepository,
                           ActorRepository actorRepository,
                           DirectorRepository directorRepository,
                           MapperUtils mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.ratingRepository = ratingRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.mapper = mapper;
    }

    @Override
    public ApiMessageResponseDto create(MovieRequestDto requestDto) {
        if (repository.existsByName(requestDto.getName())) {
            throw new BadRequestException("Já existe um filme cadastrado com o nome informado: " + requestDto.getName());
        }

        try {
            List<Category> categories = categoryRepository
                    .findAllById(requestDto.getCategoriesIds());
            Set<Category> categoriesSet = new HashSet<>(categories);

            List<Actor> actors = actorRepository.findAllById(requestDto.getActorsIds());
            Set<Actor> actorsSet = new HashSet<>(actors);

            List<Director> directors = directorRepository.findAllById(requestDto.getDirectorsIds());
            Set<Director> directorSet = new HashSet<>(directors);

            Movie movie = mapper.parseObject(requestDto,
                    Movie.class);

            movie.setCategories(categoriesSet);
            movie.setActors(actorsSet);
            movie.setDirectors(directorSet);

            Movie savedMovie = repository.save(movie);

            Rating rating = ratingRepository.save(new Rating(0.0,
                    0,
                    new Date(),
                    savedMovie));
            savedMovie.setRatings(rating);

            repository.save(savedMovie);

            return new ApiMessageResponseDto("Filme criado com sucesso!");
        } catch (DataIntegrityViolationException ex) {
            throw new SqlBadRequestException(("Ocorreu um erro: " + ex.getRootCause().getMessage()));
        }
    }
}
