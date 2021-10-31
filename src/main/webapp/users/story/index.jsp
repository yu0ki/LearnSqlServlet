<%@page import="story_dao.UserStoryShowDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>ストーリー一覧</h3></div>
		</div>
		
		<% story_dao.UserStoryIndexDAO usid = new story_dao.UserStoryIndexDAO();  %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		<% java.util.List<beans.UserStoryBeans> usb_list = usid.findAllStory(uid); %>
		
		<table class="table">
			<tr class="bg-light">
				<th>タイトル</th>
			</tr>
		
			<%for(int i = usb_list.size() - 1; i >= 0; i--){%>
		        <% beans.UserStoryBeans usb = UserStoryShowDAO.findStory(usb_list.get(i).getTitle(), uid);%>
		        <tr>
		            <td>
		            	<% if (usb.getIsFocused()) {%>
		            		<span class="text-primary"><i class="fas fa-flag"></i></span>
		            	<% } else if (!usb.getIsOpened() && !usb.getIsFocused()) { %>
		            		<span class="text-danger">NEW!</span>
		            	<% } %>
		            	<a href="/LearnSqlServlet/users/story/show.jsp?title=<%= usb.getTitle() %>"><%= usb.getTitle() %></a>
		            </td>
		        </tr>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>