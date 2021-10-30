<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>問題一覧</h3></div>
		</div>
		
		<% exercise_dao.UserExerciseIndexDAO ueid = new exercise_dao.UserExerciseIndexDAO();  %>
		<% java.util.List<beans.UserExerciseBeans> ueb_list = ueid.findAllExercise(); %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		
		<table class="table">
			<tr class="row">
				<th class="col-1 bg-light">問題id</th>
				<th class="col-1 bg-light">正誤</th>
				<th class="col-1 bg-light"></th>
				<th class="col-8 bg-light">問題文</th>
			</tr>
		
			<%for(int i = 0; i < ueb_list.size(); i++){%>
		        <% beans.UserExerciseBeans ueb = exercise_dao.UserExerciseShowDAO.findExercise(ueb_list.get(i).getEid(), uid);%>
		        <tr class="row">
		            <td class="col-1">
		            	<a href="/LearnSqlServlet/users/exercise/show.jsp?eid=<%= ueb.getEid() %>"><%= ueb.getEid() %></a>
		            </td>
		            
		            <td class="col-1">
		            	<% if (ueb.getIsCorrect()) { %>
		            		<span class="text-success"><i class="fas fa-check-circle"></i></span>
		            	<% } else if (ueb.getIsCorrect()){ %>
		            		<span class="text-danger"><i class="fas fa-times-circle"></i></span>
		            	<% } else {%>
		            		<span class="text-secondary"><i class="fas fa-question-circle"></i></span>
		            	<% } %>
		            </td>
		            
		            <td class="col-1">
		            	
		            </td>
		            
		            
		            <!-- style直指定。問題文が長すぎる場合は三点リーダーで省略 -->
		            <td style="max-width: 100%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="wrap-text col-8">
		            	<%= ueb.getSentence() %>
		            </td>
		        </tr>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>