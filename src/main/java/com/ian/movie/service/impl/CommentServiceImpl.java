package com.ian.movie.service.impl;

import com.ian.movie.exception.CommentValidationException;
import com.ian.movie.exception.NoCommentsException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.model.dao.CommentDAO;
import com.ian.movie.model.dto.CommentDTO;
import com.ian.movie.model.dto.CommentDataDTO;
import com.ian.movie.repository.CommentRepository;
import com.ian.movie.service.ICommentService;
import com.ian.movie.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    private final IMovieService movieService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(@Lazy IMovieService movieService, CommentRepository commentRepository) {
        this.movieService = movieService;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDTO> getComments(String movieId) throws NoMovieException {
        if(movieService.movieExists(movieId)) {
            List<CommentDAO> commentsDAO = commentRepository.findByMovieId(movieId);

            if (commentsDAO.isEmpty()) {
                return new ArrayList<>();
            }

            List<CommentDTO> commentsDTO = new ArrayList<>();

            for (CommentDAO commentDAO : commentsDAO) {
                commentsDTO.add(new CommentDTO(commentDAO));
            }

            return commentsDTO;
        }else{
            throw new NoMovieException(movieId);
        }
    }

    @Override
    public CommentDTO getComment(String movieId, String commentId) throws NoCommentsException, NoMovieException {
        if(movieService.movieExists(movieId)) {
            Optional<CommentDAO> optionalCommentDAO = commentRepository.findByIdAndMovieId(commentId, movieId);

            CommentDAO commentDAO = optionalCommentDAO.orElseThrow(() -> new NoCommentsException(commentId, movieId));
            return new CommentDTO(commentDAO);
        }else{
            throw new NoMovieException(movieId);
        }
    }

    @Override
    public CommentDTO postComment(String movieId, CommentDTO commentDTO) throws CommentValidationException, NoMovieException {
        if(movieService.movieExists(movieId)) {
            List<String> errors = validateCommentDTO(commentDTO);

            if (!errors.isEmpty()) {
                throw new CommentValidationException(errors);
            }

            commentDTO.getData().setDateCreated(LocalDateTime.now());
            CommentDAO commentDAO = new CommentDAO(commentDTO);
            commentDAO.setMovieId(movieId);
            return new CommentDTO(commentRepository.save(commentDAO));
        }else{
            throw new NoMovieException(movieId);
        }
    }

    @Override
    public Pair<CommentDTO, Boolean> putComment(String movieId, String commentId, CommentDTO commentDTO) throws NoMovieException, CommentValidationException {
        if(movieService.movieExists(movieId)) {
            List<String> errors = validateCommentDTO(commentDTO);

            if (!errors.isEmpty()) {
                throw new CommentValidationException(errors);
            }

            Optional<CommentDAO> existingComment = commentRepository.findById(commentId);
            boolean commentExists = existingComment.isPresent();

            CommentDAO commentDAO = new CommentDAO(commentDTO);
            commentDAO.setId(commentId);
            commentDAO.setMovieId(movieId);

            if(!commentExists){
                commentDAO.getData().setDateCreated(LocalDateTime.now());
            }else{
                commentDAO.getData().setDateCreated(existingComment.get().getData().getDateCreated());
            }

            CommentDTO commentDTONew = new CommentDTO(commentRepository.save(commentDAO));

            return Pair.of(commentDTONew, commentExists);
        }else{
            throw new NoMovieException(movieId);
        }
    }

    @Override
    public void deleteByMovieId(String movieId) {
        List<CommentDAO> commentDAOList = this.commentRepository.findByMovieId(movieId);

        for(CommentDAO comment : commentDAOList){
            commentRepository.delete(comment);
        }
    }

    @Override
    public List<String> validateCommentDTO(CommentDTO commentDTO){
        List<String> errors = new ArrayList<>();

        CommentDataDTO commentDataDTO = commentDTO.getData();

        if(commentDataDTO == null){
            errors.add("data not provided");
        }else{

            if(commentDataDTO.getMessage() == null){
                //errors.add("message not provided");
            }

            if(commentDataDTO.getRating() == null){
                errors.add("rating not provided");
            }

            if(commentDataDTO.getUser() == null){
                errors.add("user not provided");
            }
        }

        return errors;
    }
}
