package com.project.hiuni.global.filter;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CountryResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class GeoIPFilter extends OncePerRequestFilter {

    private final DatabaseReader databaseReader;
    private final List<String> allowedCountries;

    public GeoIPFilter(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
        this.allowedCountries = Arrays.asList("KR", "JP");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ipAddress = getClientIP(request);

        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);

            if(!isLocalIp(ipAddress)) {
                CountryResponse country = databaseReader.country(inetAddress);
                String countryIsoCode = country.getCountry().getIsoCode();
                log.info("[access country]: " + country.getCountry().getName() + "(" + countryIsoCode + ")");

                if (!allowedCountries.contains(countryIsoCode)) {
                    log.info("[access ip]: " + ipAddress + " " + "[denied]");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return;
                }
            } else {
                log.info("[access country]: " + "localhost");
                log.info("[access ip]: " + ipAddress);
            }

        } catch (AddressNotFoundException e) {
            log.warn("IP 주소를 찾을 수 없음: " + e);
        } catch (Exception e) {
            log.warn("IP 조회 중 오류 발생: " + e);
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private boolean isLocalIp(String ip) {

        return ip.equals("127.0.0.")
            || ip.equals("0:0:0:0:0:0:0:1")
            || ip.equals("localhost");

    }
}