<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		<!-- 該当のストーリーを検索 -->
		<% beans.AdminStoryBeans asb = story_dao.AdminStoryShowDAO.findStory(request.getParameter("title")); %>
		
		<div class="row py-3">
			<div class="col-4"><h3>ストーリー編集</h3> </div>
			<div class="col-5"></div>
			<div class="col-3">
				<a href="/LearnSqlServlet/admins/story/delete?title=<%= asb.getTitle() %>" class="btn btn-danger">削除する</a>
			</div>
			
		</div>
		
		
		
		
		<form action="./admins/story/edit" method="post">
			<table class="table">
				<tr>
					<th class="bg-light">タイトル</th>
					<td>
						<input class="form-control" type="text" name="title" value=<%= asb.getTitle() %> required> 			
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">本文</th>
					<td>
						<textarea  name="sentence" required cols="80" rows="10" class="form-control"><%= asb.getSentence() %></textarea>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">問題(後日実装)</th>
					<td>
						<select name="eid" class="form-control">
							<option value=<%= asb.getEid() %>><%= asb.getEid() %></option>										
						</select>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">次のストーリーのタイトル</th>
					<td>
						<select name="next_title" class="form-control">
							<option value="">選択してください</option>
							<% story_dao.AdminStoryIndexDAO asid = new story_dao.AdminStoryIndexDAO(); %>
							<% java.util.List<beans.AdminStoryBeans> asbs = asid.findAllStory(); %>
							<% for(int i = asbs.size() -1; i >=0; i--) { %>
								<option value=<%= asbs.get(i).getTitle() %>><%= asbs.get(i).getTitle() %></option>
							<% } %>
											
						</select>
					</td>
				</tr>
			</table>
			
			<div class="py-3">
				<input type="submit" value="更新" class="btn btn-success">
			</div>
		</form>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>