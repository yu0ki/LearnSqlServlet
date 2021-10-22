<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />

		<div class="row"><h3>ストーリー詳細</h3></div>
		
		<!-- 該当のストーリーを検索 -->
		<% beans.AdminStoryBeans asb = story_dao.AdminStoryShowDAO.findStory(request.getParameter("title")); %>
		
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