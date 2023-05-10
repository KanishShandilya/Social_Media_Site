package com.kanish.ksite.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanish.ksite.dto.CommentDto;
import com.kanish.ksite.dto.FullPostDto;
import com.kanish.ksite.dto.LikeDto;
import com.kanish.ksite.dto.PostDto;
import com.kanish.ksite.dto.PostUserDto;
import com.kanish.ksite.entity.Like;
import com.kanish.ksite.entity.Post;
import com.kanish.ksite.entity.UserInfo;
import com.kanish.ksite.helper.FileHelperUpload;
import com.kanish.ksite.repository.LikeRepository;
import com.kanish.ksite.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private UserInfoService userService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private FileHelperUpload fileHelper;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private CommentService commentService;

	public long addPost(String content,String email) {

		try {
			Post post = new Post();
			post.setContent(content);
			//post.setImgName(fileName);
			post.setUser(userService.getByEmail(email));
			post.setDateCreated(new Date());
			postRepository.save(post);
			
			return post.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public PostUserDto getPosts(String email,String user_email) {
		try {
		List<Post> lst_post = userService.getPosts(user_email);
		UserInfo u1=userService.getByEmail(email);
		UserInfo u2=userService.getByEmail(user_email);
		List<Boolean> lst_like = new ArrayList<>();
		if(!u1.getFollowing().contains(u2)) return null;

		if (lst_post.size() ==0)
			return new PostUserDto();
		Collections.sort(lst_post, new Comparator<Post>() {
			public int compare(Post o1, Post o2) {
				return o1.getDateCreated().compareTo(o2.getDateCreated());
			}
		});
		for(Post u:lst_post) {
			try {
				Like like=likeRepository.findByUserAndPost(u1, u).get();
				lst_like.add(true);
			}
			catch(Exception e) {
				lst_like.add(false);
			}
		}
		List<PostDto> posts = this.convetPostsToDto(lst_post);
		PostUserDto post_user=new PostUserDto();
		post_user.setLikes(lst_like);
		post_user.setLst_posts(posts);
		return post_user;
		}
		catch(Exception e) {e.printStackTrace();}
		return null;
		
	}

	public PostUserDto getPostForUser(String email) {
		try {
			UserInfo user = userService.getByEmail(email);
			List<UserInfo> following = user.getFollowing();
			List<Post> lst_posts = new ArrayList<>();
			List<Boolean> lst_like = new ArrayList<>();
			for (UserInfo u : following) {
				lst_posts.addAll(u.getPosts());
			}
			Collections.sort(lst_posts, new Comparator<Post>() {
				public int compare(Post o1, Post o2) {
					return o1.getDateCreated().compareTo(o2.getDateCreated());
				}
			});
			for(Post u:lst_posts) {
				try {
					Like like=likeRepository.findByUserAndPost(user, u).get();
					lst_like.add(true);
				}
				catch(Exception e) {
					lst_like.add(false);
				}
			}
			List<PostDto> posts = this.convetPostsToDto(lst_posts);
			PostUserDto post_user=new PostUserDto();
			post_user.setLikes(lst_like);
			post_user.setLst_posts(posts);
			return post_user;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<PostDto> convetPostsToDto(List<Post> lst_posts) {
		List<PostDto> posts = new ArrayList<PostDto>();
		for (Post u : lst_posts) {
			PostDto postInfo = new PostDto();
			postInfo.setContent(u.getContent());
			postInfo.setId(u.getId());
			postInfo.setImg_name(u.getImgName());
			postInfo.setEmail(u.getUser().getEmail());
			postInfo.setFirstName(u.getUser().getFirstName());
			postInfo.setLastName(u.getUser().getLastName());
			postInfo.setDateCreated(u.getDateCreated());
			postInfo.setN_comments(u.getComments().size());
			postInfo.setN_likes(u.getLikes().size());
			posts.add(postInfo);
		}
		return posts;
	}

	public byte[] getPostImg(String img_name) {

		try {
			return fileHelper.downloadFile(img_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Post getById(long id) {
		Optional<Post> postInfo = postRepository.findById(id);
		Post post = postInfo.get();
		return post;
	}

	public boolean deletePost(Long id, String email) {
		try {
			Post post = this.getById(id);
			boolean b = userService.removePost(email, post);
			if (b) {
				post.setUser(null);
				postRepository.delete(post);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean likePost(LikeDto likeDto) {

		try {
			Post post = this.getById(likeDto.getPost_id());
			Like like = new Like();
			UserInfo user = userService.getByEmail(likeDto.getUser_email());
			boolean b = userService.isFollower(user, post.getUser());

			if (!b)
				return false;
			like.setPost(post);
			like.setUser(user);
			likeRepository.save(like);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public FullPostDto getFullPost(Long post_id,String email) {

		try {
			Post u = this.getById(post_id);
			UserInfo user=userService.getByEmail(email);
			FullPostDto full_post=new FullPostDto();
			PostDto postInfo = new PostDto();
			postInfo.setContent(u.getContent());
			postInfo.setId(u.getId());
			postInfo.setImg_name(u.getImgName());
			postInfo.setEmail(u.getUser().getEmail());
			postInfo.setFirstName(u.getUser().getFirstName());
			postInfo.setLastName(u.getUser().getLastName());
			postInfo.setDateCreated(u.getDateCreated());
			postInfo.setComments(commentService.getComments(u.getComments()));
			postInfo.setN_likes(u.getLikes().size());
			postInfo.setN_comments(u.getComments().size());
			full_post.setPost(postInfo);
			try {
				Like like=likeRepository.findByUserAndPost(user, u).get();
				full_post.setLike(true);
			}
			catch(Exception e) {
				full_post.setLike(false);
			}
			return full_post;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean unLikePost(LikeDto likeDto) {
		try {
			//System.out.println(likeDto.getPost_id()+" "+likeDto.getUser_email());
			Post post = this.getById(likeDto.getPost_id());
			UserInfo user = userService.getByEmail(likeDto.getUser_email());
			Optional<Like> obj = likeRepository.findByUserAndPost(user, post);
			Like like = obj.get();
			if (post.getLikes().contains(like))
				post.getLikes().remove(like);
			like.setPost(null);
			like.setUser(null);
			likeRepository.delete(like);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean commentPost(CommentDto commentDto) {
		try {
			Post post = this.getById(commentDto.getPost_id());
			UserInfo user = userService.getByEmail(commentDto.getUser_email());
			boolean b = userService.isFollower(user, post.getUser());

			if (!b)
				return false;

			return commentService.save(commentDto.getContent(), user, post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<LikeDto> getLikes(Long post_id) {
		List<LikeDto> lst_likes = new ArrayList<>();
		try {
			Post post = this.getById(post_id);
			List<Like> likes = post.getLikes();
			for (Like u : likes) {
				LikeDto likeInfo = new LikeDto();
				likeInfo.setUser_email(u.getUser().getEmail());
				likeInfo.setPost_id(post_id);
				lst_likes.add(likeInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst_likes;
	}

	public boolean addImgPost(String fileName, long post_id) {
		try {
			Post post = this.getById(post_id);
			post.setImgName(fileName);	
			postRepository.save(post);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
