package com.android.app.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.http.Header;
import me.maxwin.view.ListViewForWebView;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ais.app.Constants;
import com.android.ais.app.R;
import com.android.app.entity.AisZxComment;
import com.android.app.util.AsyncImageLoader;

import com.android.app.util.MyHttpAPIControl;
import com.android.app.util.PreferenceUtils;
import com.android.app.util.AsyncImageLoader.ImageCallback;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NqZxCommentAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<AisZxComment> entitys = new ArrayList<AisZxComment>();
	LayoutInflater inflater;
	ListViewForWebView mListView;

	onClickButton mOnclickButton;

	AsyncImageLoader asyncImageLoader = new AsyncImageLoader(
			Constants.ITEM_IMG_WIDTH, Constants.ITEM_IMG_HEIGHT);

	public NqZxCommentAdapter(Context context, ListViewForWebView mListView) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mListView = mListView;
	}

	public void addData(ArrayList<AisZxComment> entitys) {
		if (entitys != null) {
			this.entitys.addAll(entitys);
		}
		notifyDataSetChanged();
	}

	public void addDataFromHead(AisZxComment entity) {
		if (entity != null) {
			this.entitys.add(0, entity);
		}
		notifyDataSetChanged();
	}

	public ArrayList<AisZxComment> getData() {
		return entitys;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entitys.size();
	}

	// 每个convertview都会调用此方法，获得当前所需要的view样式
	@Override
	public int getItemViewType(int position) {
		if ("1".equals(entitys.get(position).getType())) {
			return Constants.SOCIALIZATION_COMMENT; // 评论
		} else {
			return Constants.SOCIALIZATION_REPLAY; // 回复
		}
	}

	// ListVIew中包含两类布局
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return entitys.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		CommentHolder commentHolder = null;
		ReplyHolder replyHolder = null;
		int type = getItemViewType(position);

		if (convertView == null) {
			switch (type) {
			case Constants.SOCIALIZATION_COMMENT:
				commentHolder = new CommentHolder();
				convertView = inflater.inflate(
						R.layout.socialization_comment_itme, null);
				commentHolder.tv_nicheng = (TextView) convertView
						.findViewById(R.id.comment_nicheng);
				commentHolder.tv_date = (TextView) convertView
						.findViewById(R.id.comment_date);
				commentHolder.tv_content = (TextView) convertView
						.findViewById(R.id.comment_content);
				commentHolder.iv_imgs = (ImageView) convertView
						.findViewById(R.id.comment_touxiang);
				commentHolder.comment_zanCount = (TextView) convertView
						.findViewById(R.id.comment_zan_count);

				commentHolder.comment_comment_btn = (Button) convertView
						.findViewById(R.id.comment_pinglun);
				commentHolder.comment_zan_btn = (ImageView) convertView
						.findViewById(R.id.comment_zan);

				convertView.setTag(commentHolder);
				break;
			case Constants.SOCIALIZATION_REPLAY:
				replyHolder = new ReplyHolder();
				convertView = inflater.inflate(
						R.layout.socialization_replay_itme, null);
				replyHolder.tv_reply_nicheng = (TextView) convertView
						.findViewById(R.id.reply_item_nicheng);
				replyHolder.tv_reply_date = (TextView) convertView
						.findViewById(R.id.reply_item_date);
				replyHolder.tv_reply_content = (TextView) convertView
						.findViewById(R.id.reply_item_content);
				replyHolder.tv_reply_comment_nicheng = (TextView) convertView
						.findViewById(R.id.reply_comment_nicheng);
				replyHolder.tv_reply_comment_date = (TextView) convertView
						.findViewById(R.id.reply_comment_date);
				replyHolder.tv_reply_comment_content = (TextView) convertView
						.findViewById(R.id.reply_comment_content);
				replyHolder.iv_reply_comment_imgs = (ImageView) convertView
						.findViewById(R.id.reply_comment_touxiang);
				replyHolder.iv_reply_imgs = (ImageView) convertView
						.findViewById(R.id.reply_item_touxiang);
				replyHolder.tv_reply_zanCount = (TextView) convertView
						.findViewById(R.id.reply_comment_zan_count);
				replyHolder.reply_comment_zanCount = (TextView) convertView
						.findViewById(R.id.reply_item_zan_count);

				replyHolder.reply_comment_btn = (Button) convertView
						.findViewById(R.id.reply_item_pinglun);
				replyHolder.reply_zan_btn = (ImageView) convertView
						.findViewById(R.id.reply_item_zan);
				replyHolder.reply_comment_comment_btn = (Button) convertView
						.findViewById(R.id.reply_comment_zan);
				replyHolder.reply_comment_zan_btn = (ImageView) convertView
						.findViewById(R.id.reply_comment_pinglun);

				convertView.setTag(replyHolder);
				break;
			default:
				break;
			}
		} else {
			switch (type) {
			case Constants.SOCIALIZATION_COMMENT:
				commentHolder = (CommentHolder) convertView.getTag();
				break;
			case Constants.SOCIALIZATION_REPLAY:
				replyHolder = (ReplyHolder) convertView.getTag();
				break;
			default:
				break;
			}
		}

		final AisZxComment item = (AisZxComment) getItem(position);

		switch (type) {
		case Constants.SOCIALIZATION_COMMENT:
			commentHolder.tv_nicheng.setText(item.getCommentUser().getNick());
			 commentHolder.tv_date.setText(item.getTime());
			commentHolder.tv_content.setText(item.getContent());
			commentHolder.comment_zanCount.setText(item.getZancount()
					.toString());

			commentHolder.comment_comment_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (!"NONE".equals(PreferenceUtils.getPrefString(
									context, "UserId", "NONE"))
									|| !"NONE".equals(PreferenceUtils
											.getPrefString(context,
													"AnonymousUserId", "NONE"))) {
								int topicId = entitys.get(position)
										.getCommentNqlt().getId();
								int userId = entitys.get(position)
										.getCommentUser().getId();
								String commentId = entitys.get(position)
										.getId();
								String commentType = "2";
								mOnclickButton.showDialog(
										String.valueOf(topicId),
										String.valueOf(userId), commentType,
										String.valueOf(commentId));
							} else {
								mOnclickButton.showUserDialog();
							}
						}
					});
		
			if("0".equals(item.getZanFlag().trim())){
			    commentHolder.comment_zan_btn.setImageResource(R.drawable.comment_social_cl_unlike) ; 
			}else{
				 commentHolder.comment_zan_btn.setImageResource(R.drawable.comment_social_cl_like) ; 
			}
			commentHolder.comment_zan_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (!"NONE".equals(PreferenceUtils.getPrefString(
									context, "UserId", "NONE"))
									|| !"NONE".equals(PreferenceUtils
											.getPrefString(context,
													"AnonymousUserId", "NONE"))) {
								 
								if("0".equals(item.getZanFlag().trim())){
									((ImageView)(arg0.findViewById(R.id.comment_zan))).setImageResource(R.drawable.comment_social_cl_like)  ;
									item.setZanFlag("1");
									Zan(entitys.get(position).getId(),String.valueOf(entitys.get(position).getCommentUser().getId()) ,
											getItemId(position), 1);
								}else{
									item.setZanFlag("0");
									CancleZan(entitys.get(position).getId(),String.valueOf(entitys.get(position).getCommentUser().getId()) ,
											getItemId(position), 1);
									((ImageView)(arg0.findViewById(R.id.comment_zan))).setImageResource(R.drawable.comment_social_cl_unlike) ; 
								}
							} else {
								mOnclickButton.showUserDialog();
							}
						}
					});

			// 异步加载图片
			commentHolder.iv_imgs.setTag(item.getCommentUser().getIcon());
			Drawable cachedImage = asyncImageLoader.loadDrawable(item
					.getCommentUser().getIcon(), mListView,
					new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl, ListView mListView) {
							ImageView imageViewByTag = null;
							imageViewByTag = (ImageView) mListView
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null && imageDrawable != null) {
								imageViewByTag.setImageDrawable(imageDrawable);
								// Log.e("在回调里面设置好图片", "liushuai");
							} else {
								try {
									imageViewByTag
											.setImageResource(R.drawable.user_default_icon);
									// Log.e("在回调里面设置了默认的图片", "liushuai");
								} catch (Exception e) {
								}
							}
						}

					});
			commentHolder.iv_imgs
					.setImageResource(R.drawable.user_default_icon);
			if (cachedImage != null) {
				// Log.e("没在回调里设置好图片", "liushuai");
				commentHolder.iv_imgs.setImageDrawable(cachedImage);
			}

			break;
		case Constants.SOCIALIZATION_REPLAY:
			replyHolder.tv_reply_nicheng.setText(item.getCommentUser()
					.getNick());
			replyHolder.tv_reply_date.setText(item.getTime());
			replyHolder.tv_reply_content.setText(item.getContent());

			replyHolder.tv_reply_comment_nicheng.setText(item
					.getCommentedUser().getNick());
			replyHolder.tv_reply_comment_date.setText(item.getCommentedUser().getDate());
			replyHolder.tv_reply_comment_content.setText(item
					.getCommentedUser().getContent());

			replyHolder.tv_reply_zanCount.setText(item.getCommentedUser()
					.getZanCount());
			replyHolder.reply_comment_zanCount.setText(item.getZancount()
					.toString());
			
			
			
			replyHolder.reply_comment_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (!"NONE".equals(PreferenceUtils.getPrefString(
									context, "UserId", "NONE"))
									|| !"NONE".equals(PreferenceUtils
											.getPrefString(context,
													"AnonymousUserId", "NONE"))) {
								int topicId = entitys.get(position)
										.getCommentNqlt().getId();
								int userId = entitys.get(position)
										.getCommentUser().getId();
								String commentId = entitys.get(position)
										.getId();
								String commentType = "2";
								mOnclickButton.showDialog(
										String.valueOf(topicId),
										String.valueOf(userId), commentType,
										String.valueOf(commentId));
							} else {
								mOnclickButton.showUserDialog();
							}

						}
					});
			
			if("0".equals(item.getZanFlag())){
				replyHolder.reply_zan_btn.setImageResource(R.drawable.comment_social_cl_unlike) ; 
			}else{
				replyHolder.reply_zan_btn.setImageResource(R.drawable.comment_social_cl_like) ; 
			} 
			replyHolder.reply_zan_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (!"NONE".equals(PreferenceUtils.getPrefString(context,
							"UserId", "NONE"))
							|| !"NONE".equals(PreferenceUtils.getPrefString(
									context, "AnonymousUserId", "NONE"))) {
						 
						if("0".equals(item.getZanFlag())){
							((ImageView)(arg0.findViewById(R.id.reply_item_zan))).setImageResource(R.drawable.comment_social_cl_like) ; 
							item.setZanFlag("1");
							Zan(entitys.get(position).getId(), String.valueOf(entitys.get(position).getCommentUser().getId()) ,
									getItemId(position), 1);
						}else{
							item.setZanFlag("0");
							CancleZan(entitys.get(position).getId(),String.valueOf(entitys.get(position).getCommentUser().getId()) ,
									getItemId(position), 1); 
							((ImageView)(arg0.findViewById(R.id.reply_item_zan))).setImageResource(R.drawable.comment_social_cl_like)  ;
						}
					} else {
						mOnclickButton.showUserDialog();
					}

				}
			});
			replyHolder.reply_comment_comment_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (!"NONE".equals(PreferenceUtils.getPrefString(
									context, "UserId", "NONE"))
									|| !"NONE".equals(PreferenceUtils
											.getPrefString(context,
													"AnonymousUserId", "NONE"))) {
								int topicId = entitys.get(position)
										.getCommentNqlt().getId();
								int userId = entitys.get(position)
										.getCommentedUser().getId();
								String commentId = entitys.get(position).getCommentedUser().getCommentId();
								String commentType = "2";
								mOnclickButton.showDialog(
										String.valueOf(topicId),
										String.valueOf(userId), commentType,
										String.valueOf(commentId));
							} else {
								mOnclickButton.showUserDialog();
							}
						}
					});
			
			if("0".equals(item.getCommentedUser().getZanFlag())){
				replyHolder.reply_comment_zan_btn.setImageResource(R.drawable.comment_social_cl_unlike) ; 
			}else{
				replyHolder.reply_comment_zan_btn.setImageResource(R.drawable.comment_social_cl_like) ; 
			}
			replyHolder.reply_comment_zan_btn
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							if (!"NONE".equals(PreferenceUtils.getPrefString(
									context, "UserId", "NONE"))
									|| !"NONE".equals(PreferenceUtils
											.getPrefString(context,
													"AnonymousUserId", "NONE"))) {
								if("0".equals(item.getCommentedUser().getZanFlag())){
									((ImageView)(arg0.findViewById(R.id.reply_comment_pinglun))).setImageResource(R.drawable.comment_social_cl_like) ; 
									item.getCommentedUser().setZanFlag("1");
									Zan(entitys.get(position).getCommentedUser().getCommentId() , String.valueOf(entitys.get(position).getCommentedUser().getId()) ,
											getItemId(position), 2);
								}else{
									item.getCommentedUser().setZanFlag("0");
									CancleZan(entitys.get(position).getCommentedUser().getCommentId() , String.valueOf(entitys.get(position).getCommentedUser().getId()) ,
											getItemId(position), 2); 
									arg0.findViewById(R.id.reply_comment_pinglun).setTag(item.getId()+item.getCommentedUser().getId() + "unlike");
								}
							} else {
								mOnclickButton.showUserDialog();
							}
						}

					});

			// 异步加载图片
			replyHolder.iv_reply_imgs.setTag(item.getCommentUser().getIcon());
			Drawable cachedImage1 = asyncImageLoader.loadDrawable(item
					.getCommentUser().getIcon(), mListView,
					new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl, ListView mListView) {
							ImageView imageViewByTag = null;
							imageViewByTag = (ImageView) mListView
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null && imageDrawable != null) {
								imageViewByTag.setImageDrawable(imageDrawable);
								// Log.e("在回调里面设置好图片", "liushuai");
							} else {
								try {
									imageViewByTag
											.setImageResource(R.drawable.user_default_icon);
									// Log.e("在回调里面设置了默认的图片", "liushuai");
								} catch (Exception e) {
								}
							}
						}

					});
			replyHolder.iv_reply_imgs
					.setImageResource(R.drawable.user_default_icon);
			if (cachedImage1 != null) {
				// Log.e("没在回调里设置好图片", "liushuai");
				replyHolder.iv_reply_imgs.setImageDrawable(cachedImage1);
			}

			// 异步加载图片
			replyHolder.iv_reply_comment_imgs.setTag(item.getCommentedUser()
					.getIcon());
			Drawable cachedImage2 = asyncImageLoader.loadDrawable(item
					.getCommentedUser().getIcon(), mListView,
					new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl, ListView mListView) {
							ImageView imageViewByTag = null;
							imageViewByTag = (ImageView) mListView
									.findViewWithTag(imageUrl);
							if (imageViewByTag != null && imageDrawable != null) {
								imageViewByTag.setImageDrawable(imageDrawable);
								// Log.e("在回调里面设置好图片", "liushuai");
							} else {
								try {
									imageViewByTag
											.setImageResource(R.drawable.user_default_icon);
									// Log.e("在回调里面设置了默认的图片", "liushuai");
								} catch (Exception e) {
								}
							}
						}

					});
			replyHolder.iv_reply_comment_imgs
					.setImageResource(R.drawable.user_default_icon);
			if (cachedImage2 != null) {
				replyHolder.iv_reply_comment_imgs
						.setImageDrawable(cachedImage2);
			}

			break;
		default:
			break;
		}

		return convertView;
	}

	public class CommentHolder {
		ImageView iv_imgs; // 头像
		TextView tv_nicheng;
		TextView tv_date;
		TextView tv_content;
		Button comment_comment_btn;
		ImageView comment_zan_btn;
		TextView comment_zanCount;
	}

	public class ReplyHolder {
		ImageView iv_reply_comment_imgs; // 头像
		TextView tv_reply_comment_nicheng;
		TextView tv_reply_comment_date;
		TextView tv_reply_comment_content;
		ImageView iv_reply_imgs; // 头像
		TextView tv_reply_nicheng;
		TextView tv_reply_date;
		TextView tv_reply_content;
		TextView tv_reply_zanCount;

		TextView reply_comment_zanCount;
		Button reply_comment_btn;
		ImageView reply_zan_btn;
		Button reply_comment_comment_btn;
		ImageView reply_comment_zan_btn;
	}

	public void setOnclickButton(onClickButton mOnclikButton) {
		this.mOnclickButton = mOnclikButton;
	}

	public interface onClickButton {
		public void showDialog(String topic, String userId, String commentType,
				String commentFlag);

		public void showUserDialog();
	}

	public void Zan(String id, String userId , final long flag, final int pinglunOrReplyFlag) {

		RequestParams baseParams = MyHttpAPIControl.getBaseParams();
		baseParams.add("id", id);
		baseParams.add("userId", userId) ; 

		MyHttpAPIControl.newInstance().zanAisZxComment(baseParams,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(context, "网络异常,赞失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);


						if (pinglunOrReplyFlag == 1) {
							entitys.get((int) flag).setZancount(
									new BigDecimal(Integer.parseInt(entitys
											.get((int) flag).getZancount()
											.toString()) + 1));

						} else {

							entitys.get((int) flag)
									.getCommentedUser()
									.setZanCount(
											String.valueOf(Integer
													.parseInt(entitys
															.get((int) flag)
															.getCommentedUser()
															.getZanCount()
															.toString()) + 1));
						}
						notifyDataSetChanged();

						Toast.makeText(context, "赞成功 ！", Toast.LENGTH_SHORT)
								.show();

					}
				});

	}
	
	public void CancleZan(String id,   String userId , final long flag, final int pinglunOrReplyFlag) {

		RequestParams baseParams = MyHttpAPIControl.getBaseParams();
		baseParams.add("id", id);
		baseParams.add("userId", userId) ;

		MyHttpAPIControl.newInstance().cancleZanAisZxComment(baseParams,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						super.onFailure(error);
						Toast.makeText(context, "网络异常,取消赞失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String content) {
						super.onSuccess(statusCode, headers, content);
						if (pinglunOrReplyFlag == 1) {
							entitys.get((int) flag).setZancount(
									new BigDecimal(Integer.parseInt(entitys
											.get((int) flag).getZancount()
											.toString()) - 1));

						} else {

							entitys.get((int) flag)
									.getCommentedUser()
									.setZanCount(
											String.valueOf(Integer
													.parseInt(entitys
															.get((int) flag)
															.getCommentedUser()
															.getZanCount()
															.toString()) - 1));
						}
						notifyDataSetChanged();

						Toast.makeText(context, "取消赞成功 ！", Toast.LENGTH_SHORT)
								.show();

					}
				});

	}


}
