package com.kanish.ksite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanish.ksite.dto.CommentDto;
import com.kanish.ksite.dto.FullPostDto;
import com.kanish.ksite.dto.LikeDto;
import com.kanish.ksite.dto.PostDto;
import com.kanish.ksite.dto.PostUserDto;
import com.kanish.ksite.helper.FileHelperUpload;
import com.kanish.ksite.service.PostService;

import jakarta.servlet.MultipartConfigElement;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/posts")
public class PostController {
	/*
	 * addPost 
	 * get Posts of a user
	 * get posts for a user
	 * delete a post
	 * get image for post
	 * delete a post
	 * like a post
	 * get full post
	 * unlike a post
	 * comment on post
	 * get likes of a post
	 * */
	
	@Autowired
	private FileHelperUpload fileUpload;
	
	@Autowired
	private PostService postService;
	
	@PostMapping
	public ResponseEntity<?> addPost(@RequestParam(value="post") String post){
		try {
			String email=SecurityContextHolder.getContext().getAuthentication().getName();
			long b=postService.addPost(post,email);
			return ResponseEntity.ok(b);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	@PostMapping(value = "/postImg/{post_id}")
	public ResponseEntity<?> postImg(@PathVariable long post_id,@RequestParam(value="file",required = true) MultipartFile image){
		String fileName="";
		try {
			fileName=fileUpload.uploadFile(image);
			if(fileName.equals("failed")){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
			}
			else {
				boolean b=postService.addImgPost(fileName,post_id);
				if(!b)	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/user/{user_email}")
	public ResponseEntity<PostUserDto> getPostsOfUser(@PathVariable String user_email){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		PostUserDto posts=postService.getPosts(email,user_email);
		if(posts==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.ok(posts);
	}
	
	@GetMapping
	public ResponseEntity<PostUserDto> getPostsForUser(){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		PostUserDto lst_post=postService.getPostForUser(email);
		
		if(lst_post==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		return ResponseEntity.ok(lst_post);
	}
	
	@GetMapping("/postImg/{img_name}")
	public ResponseEntity<?> getPostImg(@PathVariable String img_name){
		byte[] image=postService.getPostImg(img_name);
		if(image==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.valueOf("image/png"))
					.body(image);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deletePost(@RequestParam("post_id") Long post_id){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		boolean b=postService.deletePost(post_id,email);
		if(b) {
			return ResponseEntity.ok("Deleted Successfully");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/like")
	public ResponseEntity<?> likePost(@RequestBody LikeDto likeDto){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		//System.out.println(likeDto.getPost_id()+" "+email);
		likeDto.setUser_email(email);
		boolean b=postService.likePost(likeDto);
		if(b) {
			return ResponseEntity.ok("Liked");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@GetMapping("get/{post_id}")
	public ResponseEntity<FullPostDto> getFullPost(@PathVariable Long post_id){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		FullPostDto post=postService.getFullPost(post_id,email);
		if(post==null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		else {
			return ResponseEntity.ok(post);
		}
	}
	@PostMapping("/unlike")
	public ResponseEntity<String> unLikePost(@RequestBody LikeDto likeDto){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		//System.out.println(email+" "+likeDto.getPost_id());
		likeDto.setUser_email(email);
		boolean b=postService.unLikePost(likeDto);
		if(b) {
			return ResponseEntity.ok("Unliked");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/comment")
	public ResponseEntity<?> commentPost(@RequestBody CommentDto commentDto){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		commentDto.setUser_email(email);
		boolean b=postService.commentPost(commentDto);
		if(b) {
			return ResponseEntity.ok("Comment Added");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@GetMapping("/like/{post_id}")
	public ResponseEntity<?> getLikes(@PathVariable Long post_id){
		List<LikeDto> lst_likes=postService.getLikes(post_id);
		return ResponseEntity.ok(lst_likes);
	}
}
