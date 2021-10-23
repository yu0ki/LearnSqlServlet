<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>ストーリー一覧</h3></div>
			<div class="col-5"></div>
			<div><a href="/LearnSqlServlet/admins/story/create.jsp" class="btn btn-success">新規作成</a></div>
		</div>
		
		<% story_dao.AdminStoryIndexDAO asid = new story_dao.AdminStoryIndexDAO();  %>
		<% java.util.List<beans.AdminStoryBeans> asb_list = asid.findAllStory(); %>
		
		<table class="table">
			<tr class="bg-light">
				<th>タイトル</th>
			</tr>
		
			<%for(int i = asb_list.size() - 1; i >= 0; i--){%>
		        <% beans.AdminStoryBeans asb = asb_list.get(i);%>
		        <tr>
		            <td>
		            	<a href="/LearnSqlServlet/admins/story/show.jsp?title=<%= asb.getTitle() %>"><%= asb.getTitle() %></a>
		            </td>
		        </tr>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>