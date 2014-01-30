package pe.com.apiconz.example.portlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.um.PumaLocator;
import com.ibm.portal.um.PumaProfile;
import com.ibm.portal.um.User;
import com.ibm.portal.um.exceptions.PumaException;
import com.ibm.portal.um.portletservice.PumaHome;

public class PumaPortlet extends GenericPortlet {

	private PortletServiceHome serviceHome;

	@Override
	public void init() throws PortletException {
		try {
			InitialContext ctx = new InitialContext();
			Object home = ctx
					.lookup("portletservice/com.ibm.portal.um.portletservice.PumaHome");
			if (home != null) {
				serviceHome = (PortletServiceHome) home;
			}
		} catch (Exception e) {

		}
	}

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		if (serviceHome != null) {
			PumaHome service = (PumaHome) serviceHome
					.getPortletService(PumaHome.class);
			PumaProfile profile = service.getProfile(request);
			PumaLocator plocator = service.getLocator(request);
			try {
				User usuario = profile.getCurrentUser();
				List attribNames = profile.getDefinedUserAttributeNames();
				Map userDetails = profile.getAttributes(
						profile.getCurrentUser(), attribNames);

				System.out.println("userDetails::::::" + userDetails);
				response.setContentType("text/html");
				response.getWriter().println("::::::userDetails::::::");
				for (int i = 0; i < attribNames.size(); i++) {

					response.getWriter().println(
							"<br/>" + attribNames.get(i) + ":"
									+ userDetails.get(attribNames.get(i)));
				}

			} catch (PumaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
