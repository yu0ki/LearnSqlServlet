<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		<!-- 該当の問題を検索 -->
		<% beans.AdminExerciseBeans aeb = exercise_dao.AdminExerciseShowDAO.findExercise(Integer.parseInt(request.getParameter("eid"))); %>
		
		<div class="row py-3">
			<div class="col-4"><h3>問題詳細</h3> </div>
			<div class="col-4"></div>
			<div class="col-2">
				<a href="/LearnSqlServlet/admins/exercise/edit?eid=<%= aeb.getEid() %>" class="btn btn-success">編集する</a>
			</div>
			<div class="col-2">
				<a href="/LearnSqlServlet/admins/exercise/delete?eid=<%= aeb.getEid() %>" class="btn btn-danger">削除する</a>
			</div>
			
		</div>
		
		
		
		
		
		<table class="table">
			<tr>
				<th class="bg-light">問題id</th>
				<td><%= aeb.getEid() %></td>
			</tr>
			
			<tr>
				<th class="bg-light">問題文</th>
				<td><div class="container"><%= aeb.getSentence() %></div></td>
			</tr>
			
			<tr>
				<th class="bg-light">解答例</th>
				<td>
					<%= aeb.getAnswer() %>
				</td>
			</tr>
			
			
			
			<% beans.AdminAccountBeans aab = new beans.AdminAccountBeans(); %>
			<% aab.setAdminNumber(aeb.getAdminNumber()); %>
			<% aab.setResponsibility(aeb.getResponsibility()); %>
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
						<% String str = odtFormatter.format(aeb.getEditingDate().atZoneSameInstant(java.time.ZoneId.of("Asia/Tokyo"))); %>
						<% out.println(str); %> 	
				</td>
			</tr>
		
			
			
		</table>
		
		<div class="row py-3">
			<div class="col-4"><h3>編集履歴</h3> </div>
		</div>
		
		<table class="table">
			<tr class="bg-light">
				<th>編集者</th>
				<th>連絡先</th>
				<th>備考</th>
				<th>編集日時</th>
			</tr>
			
			<% java.util.List<java.util.List<String>> logs = exercise_dao.ExerciseEditLogDAO.findExerciseLog(aeb.getEid()); %>
			<% for(int i = 0; i < logs.size(); i++) { %>
				<tr>
					<% java.util.List<String> log = logs.get(i); %>
					<td><%= log.get(0) %>(<%= log.get(1) %>)</td>
					<td><%= log.get(2) %></td>
					<td>
						<% if (!Boolean.parseBoolean(log.get(3))) { %>
							退職済み
						<% } %>
					</td>
					<td><%= log.get(4) %></td>
				</tr>
			<% } %>
		</table>
		
		
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>