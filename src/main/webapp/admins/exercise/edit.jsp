<%@page import="exercise_dao.AdminExerciseShowDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		
		
		<div class="row py-3">
			<div class="col-4"><h3>問題編集</h3> </div>
		</div>
		
		
		<% int eid = Integer.parseInt(request.getParameter("eid")); %>
		
		<form action="./edit?eid=<%= eid %>" method="post">
			<table class="table">
				
				<% beans.AdminExerciseBeans aeb = AdminExerciseShowDAO.findExercise(eid); %>
				<tr>
					<th class="bg-light">問題文</th>
					<td>
						<textarea  name="new_sentence" required cols="80" rows="10" class="form-control" autofocus><%= aeb.getSentence() %></textarea>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">解答例</th>
					<td>
						<textarea  name="new_answer" required cols="80" rows="5" class="form-control"><%= aeb.getAnswer() %></textarea>
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