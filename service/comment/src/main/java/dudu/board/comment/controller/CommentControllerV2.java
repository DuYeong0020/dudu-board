package dudu.board.comment.controller;

import dudu.board.comment.service.CommentServiceV2;
import dudu.board.comment.service.request.CommentCreateRequestV2;
import dudu.board.comment.service.response.CommentPageResponse;
import dudu.board.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentControllerV2 {

    private final CommentServiceV2 commentService;

    @GetMapping("/v2/comments/{commentId}")
    public CommentResponse read(
            @PathVariable("commentId") Long commentId
    ) {
        return commentService.read(commentId);
    }

    @PostMapping("/v2/comments")
    public CommentResponse create(@RequestBody CommentCreateRequestV2 request) {
        return commentService.create(request);
    }


    @GetMapping("/v2/comments")
    public CommentPageResponse readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAll(articleId, page, pageSize);
    }

    @GetMapping("/v2/comments/infinite-scroll")
    public List<CommentResponse> readAllInfiniteScroll(
            @RequestParam("articleId") Long articleId,
            @RequestParam(value = "lastPath", required = false) String lastPath,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAllInfiniteScroll(articleId, lastPath, pageSize);
    }

    @DeleteMapping("/v2/comments/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}

