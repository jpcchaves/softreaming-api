package com.jpcchaves.softreaming.services.impl;

import com.jpcchaves.softreaming.entities.Director;
import com.jpcchaves.softreaming.entities.Movie;
import com.jpcchaves.softreaming.exceptions.BadRequestException;
import com.jpcchaves.softreaming.payload.dtos.ApiMessageResponseDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorDto;
import com.jpcchaves.softreaming.payload.dtos.directors.DirectorsPaginatedDto;
import com.jpcchaves.softreaming.payload.dtos.movie.MovieResponseMinDto;
import com.jpcchaves.softreaming.repositories.DirectorRepository;
import com.jpcchaves.softreaming.services.DirectorsService;
import com.jpcchaves.softreaming.utils.mapper.MapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DirectorsServiceImpl implements DirectorsService {
    private final DirectorRepository directorRepository;
    private final MapperUtils mapper;

    public DirectorsServiceImpl(DirectorRepository directorRepository,
                                MapperUtils mapper) {
        this.directorRepository = directorRepository;
        this.mapper = mapper;
    }

    @Override
    public ApiMessageResponseDto create(DirectorDto directorDto) {
        Director director = mapper.parseObject(directorDto, Director.class);
        directorRepository.save(director);
        return makeApiResponse("Diretor criado com sucesso: " + directorDto.getFirstName() + " " + directorDto.getLastName());
    }

    @Override
    public DirectorsPaginatedDto getAll(Pageable pageable) {
        Page<Director> directors = directorRepository.findAll(pageable);
        DirectorsPaginatedDto directorsPaginatedDto = new DirectorsPaginatedDto();

        directorsPaginatedDto.setContent(mapper.parseListObjects(directors.getContent(), DirectorDto.class));
        directorsPaginatedDto.setPageNo(directors.getNumber());
        directorsPaginatedDto.setPageSize(directors.getSize());
        directorsPaginatedDto.setTotalElements(directors.getTotalElements());
        directorsPaginatedDto.setTotalPages(directors.getTotalPages());
        directorsPaginatedDto.setLast(directors.isLast());

        return directorsPaginatedDto;
    }

    @Override
    public DirectorDto getById(Long id) {
        Director director = getDirectorById(id);
        return mapper.parseObject(director, DirectorDto.class);
    }

    @Override
    public ApiMessageResponseDto update(Long id,
                                        DirectorDto directorDto) {
        Director director = getDirectorById(id);

        director.setFirstName(directorDto.getFirstName());
        director.setLastName(directorDto.getLastName());

        directorRepository.save(director);
        return makeApiResponse("Diretor atualizado com sucesso!");
    }

    @Override
    public Set<MovieResponseMinDto> getAllMoviesByDirector(Long id) {
        Director director = getDirectorById(id);
        Set<Movie> movies = director.getMovies();
        return mapper.parseSetObjects(movies, MovieResponseMinDto.class);
    }

    private Director getDirectorById(Long id) {
        return directorRepository.findById(id).orElseThrow(() -> new BadRequestException("Diretor não encontrado com o ID informado: " + id));
    }

    private ApiMessageResponseDto makeApiResponse(String message) {
        return new ApiMessageResponseDto(message);
    }
}
