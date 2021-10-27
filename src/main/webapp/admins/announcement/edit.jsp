<%@page import="announcement_dao.AdminAnnouncementShowDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		
		
		<div class="row py-3">
			<div class="col-4"><h3>告知編集</h3> </div>
		</div>
		
		
		<% int aid = Integer.parseInt(request.getParameter("aid")); %>
		
		<form action="./edit?aid=<%= aid %>" method="post">
			<table class="table">
				
				<% beans.AdminAnnouncementBeans aanb = announcement_dao.AdminAnnouncementShowDAO.findAnnouncement(aid); %>
				<tr>
					<th class="bg-light">タイトル</th>
					<td>
						<textarea  name="new_title" required cols="80" rows="1" class="form-control" autofocus><%= aanb.getTitle() %></textarea>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">本文</th>
					<td>
						<textarea  name="new_sentence" required cols="80" rows="10" class="form-control"><%= aanb.getSentence() %></textarea>
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td><input type="submit" value="更新" class="btn btn-success"></td>
				</tr>
			</table>
			
			
		</form>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>