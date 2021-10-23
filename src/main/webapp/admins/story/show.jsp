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
				<td>
					<% if (asb.getEid() != 0) { %>
						<%= asb.getEid() %>
					<% } else { %>
						あとで決める
					<% } %>
				</td>
			</tr>
			
			<tr>
				<th class="bg-light">次のストーリーのタイトル</th>
				<td>
					<% if (asb.getNextTitle() == null) { %>
						あとで決める
					<% } else { %>
						<%= asb.getNextTitle() %>
					<% } %>
				</td>
			</tr>
			
			<% beans.AdminAccountBeans aab = new beans.AdminAccountBeans(); %>
			<% aab.setAdminNumber(asb.getAdminNumber()); %>
			<% aab.setResponsibility(asb.getResponsibility()); %>
			<% aab = account_dao.AdminAccountDAO.findAdminAccount(aab);  %>
			
			<tr>
				<th class="bg-light">最終編集者</th>
				<td><%= aab.getName() %>（<%= aab.getResponsibility() %>）</td>
			</tr>
			
			<tr>
				<th class="bg-light">連絡先</th>
				<td><%= aab.getContact() %></td>
			</tr>
			
			<tr>
				<th class="bg-light">最終編集日時</th>
				<td><% java.time.format.DateTimeFormatter odtFormatter = java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;%>
						<% String str = odtFormatter.format(asb.getEditingDate().atZoneSameInstant(java.time.ZoneId.of("Asia/Tokyo"))); %>
						<% out.println(str); %> 	
				</td>
			</tr>
		
			
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>