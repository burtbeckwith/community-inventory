package scott.kellie

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken

import javax.naming.AuthenticationException

/**
 * Generated by the Shiro plugin. This filters class protects all URLs
 * via access control by convention.
 */
class SecurityFilters {
    SecurityService securityService

    def filters = {
        api(uri: "/api/**/**") {
            before = {
                request.api = true
                log.debug('api call set to true')
            }
        }

        nonApi(uri: "/**") {
                before = {
                    // Ignore direct views (e.g. the default main index page).
                    if (!controllerName) return true

                    if (request.api) {
                        log.debug('api call is true, so will try to authenticate')
//                    String access_token = params.accessToken
//                    ApiAccessToken token = new ApiAccessToken(apiAccessToken: access_token)
//                    try {
//                        SecurityUtils.subject.login(token)
//                        accessControl()
//                    } catch (AuthenticationException) {
//                        response
//                    }
//                        UsernamePasswordToken token = new UsernamePasswordToken(username: params.username, password: params.password)
//                        try {
//                            SecurityUtils.subject.login(token)
//                            accessControl()
//                        } catch (AuthenticationException){
//                            response
//                        }
                        if(securityService.apiLogin(params.username, params.password)){
                            log.debug('authenticated with api call')
//                            accessControl()
                        } else {
                            log.debug('couldnt authenticate with api call')
                            response.setStatus(401) //unauthorized
                        }


                    }
                    else {
                        log.debug('non api call')
                        // Access control by convention.
//                        accessControl()
                    }
                }
            }

    }
}
