<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		<!-- 該当のストーリーを検索 -->
		<% beans.AdminStoryBeans asb = story_dao.AdminStoryShowDAO.findStory(request.getParameter("title")); %>
		
		<div class="row py-3">
			<div class="col-4"><h3>ストーリー詳細</h3> </div>
			<div class="col-5"></div>
			<div class="col-3">
				<a href="/LearnSqlServlet/admins/story/edit?title=<%= asb.getTitle() %>" class="btn btn-success">編集する</a>
			</div>
			
		</div>
		
		
		
		
		
		<table class="table">
			<tr>
				<th class="bg-light">タイトル</th>
				<td><%= asb.getTitle() %></td>
			</tr>
			
			<tr>
				<th class="bg-light">本文</th>
				<td><div class="container"><%= asb.getSentence() %></div></td>
			</tr>
			
			<tr>
				<th class="bg-light">問題(後日実装)</th>
				<td><%= asb.getEid() %></td>
			</tr>
			
			<tr>
				<th class="bg-light">次のストーリーのタイトル</th>
				<td><%= asb.getNextTitle() %></td>
			</tr>
		
			
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>