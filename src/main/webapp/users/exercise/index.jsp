<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>問題一覧</h3></div>
			<div class="col-2"></div>
			<div class="col-5">
				<form action="./index.jsp" method="get" class="d-inline-block mx-1">
					<input type="hidden" name="display_option" value = "0">
					<input type="submit" value="昇順一覧" class="btn btn-sm btn-outline-dark">
				</form>
				
				<form action="./index.jsp" method="get" class="d-inline-block mx-1">
					<input type="hidden" name="display_option" value = "1">
					<input type="submit" value="降順一覧" class="btn btn-sm btn-outline-dark">
				</form>
				
				<form action="./index.jsp" method="get" class="d-inline-block mx-1">
					<input type="hidden" name="display_option" value = "2">
					<input type="submit" value="苦手一覧" class="btn btn-sm btn-outline-dark">
				</form>
				
				<form action="./index.jsp" method="get" class="d-inline-block mx-1">
					<input type="hidden" name="display_option" value = "3">
					<input type="submit" value="苦手 + ブックマーク一覧" class="btn btn-sm btn-outline-dark">
				</form>
			</div>
		</div>
		
		<% int display_option = 0; %>
		<% if (request.getParameter("display_option") != null) { %>
			<% display_option = Integer.parseInt(request.getParameter("display_option")); %>
		<% } %>
		
		<% exercise_dao.UserExerciseIndexDAO ueid = new exercise_dao.UserExerciseIndexDAO();  %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		
		<% java.util.List<beans.UserExerciseBeans> ueb_list = ueid.findAllExercise(display_option, uid); %>
		
		
		
				
		<table class="table">
			<tr class="row">
				<th class="col-1 bg-light">問題id</th>
				<th class="col-1 bg-light">正誤</th>
				<th class="col-8 bg-light">問題文</th>
				<th class="col-1 bg-light"></th>
			</tr>
		
			<%for(int i = 0; i < ueb_list.size(); i++){%>
		        <% beans.UserExerciseBeans ueb = exercise_dao.UserExerciseShowDAO.findExercise(ueb_list.get(i).getEid(), uid);%>
		        <% if (display_option == 0 || display_option == 1 || (display_option == 2 && !ueb.getIsCorrect()) || (display_option == 3 && (!ueb.getIsCorrect() || ueb.getIsBookmarked()))) { %>
			        <tr class="row">
			            <td class="col-1">
			            	<a href="/LearnSqlServlet/users/exercise/show.jsp?eid=<%= ueb.getEid() %>"><%= ueb.getEid() %></a>
			            </td>
			            
			            <td class="col-1">
			            	<% if (ueb.getIsCorrect()) { %>
			            		<span class="text-success"><i class="fas fa-check-circle"></i></span>
			            	<% } else if (ueb.getMyAnswer() == null) {%>
			            		<!-- 何も表示しない -->
			            	<% } else if (!ueb.getIsCorrect()){ %>
			            		<span class="text-danger"><i class="fas fa-times-circle"></i></span>
			            	<% } %>
			            </td>
			            
			            <!-- style直指定。問題文が長すぎる場合は三点リーダーで省略 -->
			            <td style="max-width: 100%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="wrap-text col-8">
			            	<%= ueb.getSentence() %>
			            </td>
			            
			             <td class="col-1">
			            	<% if (ueb.getIsBookmarked()) { %>
			            		<a href="/LearnSqlServlet/users/exercise/bookmark?eid=<%= ueb.getEid() %>&uid=<%= uid %>">
			            			<span class="text-primary"><i class="fas fa-bookmark"></i></span>
			            		</a>
			            	<% } else if (!ueb.getIsBookmarked()){ %>
			            		<a href="/LearnSqlServlet/users/exercise/bookmark?eid=<%= ueb.getEid() %>&uid=<%= uid %>">
			            			<span class="text-primary"><i class="far fa-bookmark"></i></span>
			            		</a>
			            	<% } else {%>
			            		<span class="text-secondary"><i class="fas fa-question-circle"></i></span>
			            	<% } %>
			            </td>
			        </tr>
				<% } %>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>